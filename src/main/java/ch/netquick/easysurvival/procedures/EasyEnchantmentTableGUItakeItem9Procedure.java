package ch.netquick.easysurvival.procedures;

import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.CapabilityItemHandler;

import net.minecraft.world.IWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.block.Blocks;

import java.util.Map;

import ch.netquick.easysurvival.EasysurvivalModElements;
import ch.netquick.easysurvival.EasysurvivalMod;

@EasysurvivalModElements.ModElement.Tag
public class EasyEnchantmentTableGUItakeItem9Procedure extends EasysurvivalModElements.ModElement {
	public EasyEnchantmentTableGUItakeItem9Procedure(EasysurvivalModElements instance) {
		super(instance, 76);
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("x") == null) {
			if (!dependencies.containsKey("x"))
				EasysurvivalMod.LOGGER.warn("Failed to load dependency x for procedure EasyEnchantmentTableGUItakeItem9!");
			return;
		}
		if (dependencies.get("y") == null) {
			if (!dependencies.containsKey("y"))
				EasysurvivalMod.LOGGER.warn("Failed to load dependency y for procedure EasyEnchantmentTableGUItakeItem9!");
			return;
		}
		if (dependencies.get("z") == null) {
			if (!dependencies.containsKey("z"))
				EasysurvivalMod.LOGGER.warn("Failed to load dependency z for procedure EasyEnchantmentTableGUItakeItem9!");
			return;
		}
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				EasysurvivalMod.LOGGER.warn("Failed to load dependency world for procedure EasyEnchantmentTableGUItakeItem9!");
			return;
		}
		double x = dependencies.get("x") instanceof Integer ? (int) dependencies.get("x") : (double) dependencies.get("x");
		double y = dependencies.get("y") instanceof Integer ? (int) dependencies.get("y") : (double) dependencies.get("y");
		double z = dependencies.get("z") instanceof Integer ? (int) dependencies.get("z") : (double) dependencies.get("z");
		IWorld world = (IWorld) dependencies.get("world");
		double counter = 0;
		double self = 0;
		self = (double) 9;
		counter = (double) 0;
		for (int index0 = 0; index0 < (int) (10); index0++) {
			if (((counter) != (self))) {
				{
					TileEntity _ent = world.getTileEntity(new BlockPos((int) x, (int) y, (int) z));
					if (_ent != null) {
						final int _sltid = (int) ((counter));
						final ItemStack _setstack = new ItemStack(Blocks.AIR, (int) (1));
						_setstack.setCount((int) 1);
						_ent.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(capability -> {
							if (capability instanceof IItemHandlerModifiable) {
								((IItemHandlerModifiable) capability).setStackInSlot(_sltid, _setstack);
							}
						});
					}
				}
			}
			counter = (double) ((counter) + 1);
		}
		{
			TileEntity _ent = world.getTileEntity(new BlockPos((int) x, (int) y, (int) z));
			if (_ent != null) {
				final int _sltid = (int) (10);
				final int _amount = (int) 5;
				_ent.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(capability -> {
					if (capability instanceof IItemHandlerModifiable) {
						ItemStack _stk = capability.getStackInSlot(_sltid).copy();
						_stk.shrink(_amount);
						((IItemHandlerModifiable) capability).setStackInSlot(_sltid, _stk);
					}
				});
			}
		}
	}
}
