package com.shesky17.sk17ump9.guns;

import com.shesky17.sk17ump9.Main;
import com.shesky17.sk17ump9.ammos.BulletBase;
import com.shesky17.sk17ump9.init.ModBullet;
import com.shesky17.sk17ump9.init.ModGuns;
import com.shesky17.sk17ump9.util.IHasModel;
import com.shesky17.sk17ump9.util.handler.SoundHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;


public class GunBase extends ItemBow implements IHasModel
{
    public GunBase(String name)
    {
        setUnlocalizedName(name);
        setRegistryName(name);
        this.maxStackSize = 1;
        this.setMaxDamage(500);
        this.setCreativeTab(CreativeTabs.COMBAT);
        ModGuns.GUN_LIST.add(this);
    }

    protected boolean is9MMGun(ItemStack stack){
        if(stack.getItem() == ModBullet.NINEMIL){
            return true;
        }
        return false;
    }

    @Override
    //FIXME: need to make it continue shootings after timer ends
    public int getItemEnchantability() { return 2; }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) { return 5; }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        playerIn.setActiveHand(EnumHand.MAIN_HAND);
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    public ActionResult<ItemStack> onItemRightClick2(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        boolean flag = !this.findAmmo2(playerIn).isEmpty();//true when has ammo

        ActionResult<ItemStack> ret = net.minecraftforge.event.ForgeEventFactory.onArrowNock(itemstack, worldIn, playerIn, handIn, flag);
        if (ret != null) return ret;

        if (!playerIn.capabilities.isCreativeMode && !flag) {
            return flag ? new ActionResult(EnumActionResult.PASS, itemstack) : new ActionResult(EnumActionResult.FAIL, itemstack);
        }
        else {
            playerIn.setActiveHand(handIn);
            return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
        }
    }


    @Override
    //FIXME: need to fix automatic firing and pickup status
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft)
    {
        if (entityLiving instanceof EntityPlayer)
        {
            EntityPlayer entityplayer = (EntityPlayer)entityLiving;

            //NOTE: flag is true when is in creative mode or has infinity enchantment
            boolean flag = entityplayer.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0;
            ItemStack itemstack = this.findAmmo2(entityplayer);

            int i = this.getMaxItemUseDuration(stack) - timeLeft;
            i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(stack, worldIn, entityplayer, i, !itemstack.isEmpty() || flag);
            if (i < 0) return;

            if (!itemstack.isEmpty() || flag)
            {
                if (itemstack.isEmpty()) {
                    itemstack = new ItemStack(ModBullet.NINEMIL); //Items.ARROW);
                }

                float f = getArrowVelocity2();
                //BUG: needs to make only gun can shoot bullet, not bow
                if ((double)f >= 0D ){
                    //true when creative mode or infinite ammo
//                    boolean flag1 = entityplayer.capabilities.isCreativeMode || (itemstack.getItem() instanceof ItemArrow && ((ItemArrow) itemstack.getItem()).isInfinite(itemstack, stack, entityplayer));
                    boolean flag1 = entityplayer.capabilities.isCreativeMode || (itemstack.getItem() instanceof BulletBase && ((BulletBase) itemstack.getItem()).isInfinite(itemstack, stack, entityplayer));

                    if (!worldIn.isRemote) {
                        //BUG: in the Bow class, 9mm instanceof Arrow is always true, so bow can fire bullet
                        BulletBase itembullet = (BulletBase)(itemstack.getItem() instanceof BulletBase ? itemstack.getItem() : ModBullet.NINEMIL);
                        EntityArrow entityBullet = itembullet.createArrow(worldIn, itemstack, entityplayer);
                        entityBullet.shoot(entityplayer, entityplayer.rotationPitch, entityplayer.rotationYaw, 0.0F, f * 3.0F, 1.0F);

                        if (f == 1.0F) {
                            entityBullet.setIsCritical(true);
                        }

                        int j = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);
                        if (j > 0) {
                            entityBullet.setDamage(entityBullet.getDamage() + (double)j * 0.5D + 0.5D);
                        }

                        int k = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, stack);
                        if (k > 0) {
                            entityBullet.setKnockbackStrength(k);
                        }
                        if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, stack) > 0) {
                            entityBullet.setFire(100);
                        }

                        stack.damageItem(1, entityplayer);
                        //Bullet will never be able to pickup again
                        //if (flag1 || entityplayer.capabilities.isCreativeMode && (itemstack.getItem() == Items.SPECTRAL_ARROW || itemstack.getItem() == Items.TIPPED_ARROW)) {
                        entityBullet.pickupStatus = EntityArrow.PickupStatus.DISALLOWED;
                        //}

                        //NEVER disable this, or bullet wont even spawn after firing
                        worldIn.spawnEntity(entityBullet);
                    }

                    worldIn.playSound((EntityPlayer)null,
                            entityplayer.posX,
                            entityplayer.posY,
                            entityplayer.posZ,
                            SoundHandler.ump_shooting,
                            SoundCategory.PLAYERS,
                            1.0F,
                            1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);

                    //if not creative, reduce stack by 1 after each shot
                    if (!flag1 && !entityplayer.capabilities.isCreativeMode) {
                        itemstack.shrink(1);
                        if (itemstack.isEmpty()) {
                            entityplayer.inventory.deleteStack(itemstack);
                        }
                    }

                    entityplayer.addStat(StatList.getObjectUseStats(this));
                }
            }
        }
    }

    //because findAmmo is now private, copying makes life easier....
    private ItemStack findAmmo2(EntityPlayer player)
    {
        if (this.is9MMGun(player.getHeldItem(EnumHand.OFF_HAND))) {
            return player.getHeldItem(EnumHand.OFF_HAND);
        }
        else if (this.is9MMGun(player.getHeldItem(EnumHand.MAIN_HAND))) {
            return player.getHeldItem(EnumHand.MAIN_HAND);
        }
        else {
            for (int i = 0; i < player.inventory.getSizeInventory(); ++i) {
                ItemStack itemstack = player.inventory.getStackInSlot(i);
                if (this.is9MMGun(itemstack)) {
                    return itemstack;
                }
            }
            return ItemStack.EMPTY;
        }
    }

    //Same reason, i copy this cuz i can't override it
    //Standard 9mm has a muzzle speed of 380m/s, one block = 1m, so i guess....
    public static float getArrowVelocity2() {return 380;}


    @Override
    public void registerModels() {
        Main.proxy.registerItemRenderer(this, 0, "inventory");
    }
}
