package ch.netquick.easysurvival.procedures;

import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.CapabilityItemHandler;

import net.minecraft.world.IWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tags.ItemTags;
import net.minecraft.item.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.block.Blocks;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Map;

import ch.netquick.easysurvival.EasysurvivalModElements;
import ch.netquick.easysurvival.EasysurvivalMod;

@EasysurvivalModElements.ModElement.Tag
public class EasyConverterUpdateTickProcedure extends EasysurvivalModElements.ModElement {
	public EasyConverterUpdateTickProcedure(EasysurvivalModElements instance) {
		super(instance, 138);
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("x") == null) {
			if (!dependencies.containsKey("x"))
				EasysurvivalMod.LOGGER.warn("Failed to load dependency x for procedure EasyConverterUpdateTick!");
			return;
		}
		if (dependencies.get("y") == null) {
			if (!dependencies.containsKey("y"))
				EasysurvivalMod.LOGGER.warn("Failed to load dependency y for procedure EasyConverterUpdateTick!");
			return;
		}
		if (dependencies.get("z") == null) {
			if (!dependencies.containsKey("z"))
				EasysurvivalMod.LOGGER.warn("Failed to load dependency z for procedure EasyConverterUpdateTick!");
			return;
		}
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				EasysurvivalMod.LOGGER.warn("Failed to load dependency world for procedure EasyConverterUpdateTick!");
			return;
		}
		double x = dependencies.get("x") instanceof Integer ? (int) dependencies.get("x") : (double) dependencies.get("x");
		double y = dependencies.get("y") instanceof Integer ? (int) dependencies.get("y") : (double) dependencies.get("y");
		double z = dependencies.get("z") instanceof Integer ? (int) dependencies.get("z") : (double) dependencies.get("z");
		IWorld world = (IWorld) dependencies.get("world");
		ItemStack InputStack = ItemStack.EMPTY;
		ItemStack OutputStack = ItemStack.EMPTY;
		double StackSize = 0;
		double Amount = 0;
		double InAmount = 0;
		InputStack = (new Object() {
			public ItemStack getItemStack(BlockPos pos, int sltid) {
				AtomicReference<ItemStack> _retval = new AtomicReference<>(ItemStack.EMPTY);
				TileEntity _ent = world.getTileEntity(pos);
				if (_ent != null) {
					_ent.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(capability -> {
						_retval.set(capability.getStackInSlot(sltid).copy());
					});
				}
				return _retval.get();
			}
		}.getItemStack(new BlockPos((int) x, (int) y, (int) z), (int) (0)));
		if ((((InputStack).getItem() == new ItemStack(Blocks.OAK_LOG, (int) (1)).getItem())
				|| ((InputStack).getItem() == new ItemStack(Blocks.STRIPPED_OAK_WOOD, (int) (1)).getItem()))) {
			StackSize = (double) 64;
			Amount = (double) 1;
			InAmount = (double) 1;
			OutputStack = new ItemStack(Blocks.OAK_PLANKS, (int) (1));
		}
		if ((((InputStack).getItem() == new ItemStack(Blocks.SPRUCE_LOG, (int) (1)).getItem())
				|| ((InputStack).getItem() == new ItemStack(Blocks.STRIPPED_SPRUCE_WOOD, (int) (1)).getItem()))) {
			StackSize = (double) 64;
			InAmount = (double) 1;
			Amount = (double) 1;
			OutputStack = new ItemStack(Blocks.SPRUCE_PLANKS, (int) (1));
		}
		if ((((InputStack).getItem() == new ItemStack(Blocks.BIRCH_LOG, (int) (1)).getItem())
				|| ((InputStack).getItem() == new ItemStack(Blocks.STRIPPED_BIRCH_LOG, (int) (1)).getItem()))) {
			StackSize = (double) 64;
			InAmount = (double) 1;
			Amount = (double) 1;
			OutputStack = new ItemStack(Blocks.BIRCH_PLANKS, (int) (1));
		}
		if ((((InputStack).getItem() == new ItemStack(Blocks.JUNGLE_LOG, (int) (1)).getItem())
				|| ((InputStack).getItem() == new ItemStack(Blocks.STRIPPED_JUNGLE_LOG, (int) (1)).getItem()))) {
			StackSize = (double) 64;
			InAmount = (double) 1;
			Amount = (double) 1;
			OutputStack = new ItemStack(Blocks.JUNGLE_PLANKS, (int) (1));
		}
		if ((((InputStack).getItem() == new ItemStack(Blocks.ACACIA_LOG, (int) (1)).getItem())
				|| ((InputStack).getItem() == new ItemStack(Blocks.STRIPPED_ACACIA_LOG, (int) (1)).getItem()))) {
			StackSize = (double) 64;
			InAmount = (double) 1;
			Amount = (double) 1;
			OutputStack = new ItemStack(Blocks.ACACIA_PLANKS, (int) (1));
		}
		if ((((InputStack).getItem() == new ItemStack(Blocks.DARK_OAK_LOG, (int) (1)).getItem())
				|| ((InputStack).getItem() == new ItemStack(Blocks.STRIPPED_DARK_OAK_LOG, (int) (1)).getItem()))) {
			StackSize = (double) 64;
			InAmount = (double) 1;
			Amount = (double) 1;
			OutputStack = new ItemStack(Blocks.DARK_OAK_PLANKS, (int) (1));
		}
		if ((((InputStack).getItem() == new ItemStack(Blocks.WARPED_STEM, (int) (1)).getItem())
				|| ((InputStack).getItem() == new ItemStack(Blocks.STRIPPED_WARPED_STEM, (int) (1)).getItem()))) {
			StackSize = (double) 64;
			InAmount = (double) 1;
			Amount = (double) 1;
			OutputStack = new ItemStack(Blocks.WARPED_PLANKS, (int) (1));
		}
		if ((((InputStack).getItem() == new ItemStack(Blocks.CRIMSON_STEM, (int) (1)).getItem())
				|| ((InputStack).getItem() == new ItemStack(Blocks.STRIPPED_CRIMSON_STEM, (int) (1)).getItem()))) {
			StackSize = (double) 64;
			InAmount = (double) 1;
			Amount = (double) 1;
			OutputStack = new ItemStack(Blocks.CRIMSON_PLANKS, (int) (1));
		}
		if (((InputStack).getItem() == new ItemStack(Blocks.BONE_BLOCK, (int) (1)).getItem())) {
			StackSize = (double) 64;
			InAmount = (double) 1;
			Amount = (double) 9;
			OutputStack = new ItemStack(Items.BONE_MEAL, (int) (1));
		}
		if (((InputStack).getItem() == new ItemStack(Items.BONE, (int) (1)).getItem())) {
			StackSize = (double) 64;
			InAmount = (double) 1;
			Amount = (double) 3;
			OutputStack = new ItemStack(Items.BONE_MEAL, (int) (1));
		}
		if ((ItemTags.getCollection().getTagByID(new ResourceLocation(("forge:planks").toLowerCase(java.util.Locale.ENGLISH)))
				.contains((InputStack).getItem()))) {
			StackSize = (double) 64;
			InAmount = (double) 1;
			Amount = (double) 1;
			OutputStack = new ItemStack(Items.COAL, (int) (1));
		}
		if (((InputStack).getItem() == new ItemStack(Items.GOLD_NUGGET, (int) (1)).getItem())) {
			StackSize = (double) 64;
			InAmount = (double) 9;
			Amount = (double) 1;
			OutputStack = new ItemStack(Items.GOLD_INGOT, (int) (1));
		}
		if (((InputStack).getItem() == new ItemStack(Blocks.GOLD_ORE, (int) (1)).getItem())) {
			StackSize = (double) 64;
			InAmount = (double) 1;
			Amount = (double) 1;
			OutputStack = new ItemStack(Items.GOLD_INGOT, (int) (1));
		}
		if (((InputStack).getItem() == new ItemStack(Blocks.NETHER_GOLD_ORE, (int) (1)).getItem())) {
			StackSize = (double) 64;
			InAmount = (double) 1;
			Amount = (double) 1;
			OutputStack = new ItemStack(Items.GOLD_INGOT, (int) (1));
		}
		if (((InputStack).getItem() == new ItemStack(Blocks.IRON_ORE, (int) (1)).getItem())) {
			StackSize = (double) 64;
			InAmount = (double) 1;
			Amount = (double) 1;
			OutputStack = new ItemStack(Items.IRON_INGOT, (int) (1));
		}
		if (((InputStack).getItem() == new ItemStack(Items.IRON_NUGGET, (int) (1)).getItem())) {
			StackSize = (double) 64;
			InAmount = (double) 9;
			Amount = (double) 1;
			OutputStack = new ItemStack(Items.IRON_INGOT, (int) (1));
		}
		if (((InputStack).getItem() == new ItemStack(Items.IRON_INGOT, (int) (1)).getItem())) {
			StackSize = (double) 64;
			InAmount = (double) 9;
			Amount = (double) 1;
			OutputStack = new ItemStack(Blocks.IRON_BLOCK, (int) (1));
		}
		if (((InputStack).getItem() == new ItemStack(Items.GOLD_INGOT, (int) (1)).getItem())) {
			StackSize = (double) 64;
			InAmount = (double) 9;
			Amount = (double) 1;
			OutputStack = new ItemStack(Blocks.GOLD_BLOCK, (int) (1));
		}
		if (((InputStack).getItem() == new ItemStack(Items.COAL, (int) (1)).getItem())) {
			StackSize = (double) 64;
			InAmount = (double) 9;
			Amount = (double) 1;
			OutputStack = new ItemStack(Blocks.COAL_BLOCK, (int) (1));
		}
		if (((InputStack).getItem() == new ItemStack(Items.DIAMOND, (int) (1)).getItem())) {
			StackSize = (double) 64;
			InAmount = (double) 9;
			Amount = (double) 1;
			OutputStack = new ItemStack(Blocks.DIAMOND_BLOCK, (int) (1));
		}
		if (((InputStack).getItem() == new ItemStack(Items.NETHERITE_INGOT, (int) (1)).getItem())) {
			StackSize = (double) 64;
			InAmount = (double) 9;
			Amount = (double) 1;
			OutputStack = new ItemStack(Blocks.NETHERITE_BLOCK, (int) (1));
		}
		if (((InputStack).getItem() == new ItemStack(Items.REDSTONE, (int) (1)).getItem())) {
			StackSize = (double) 64;
			InAmount = (double) 9;
			Amount = (double) 1;
			OutputStack = new ItemStack(Blocks.REDSTONE_BLOCK, (int) (1));
		}
		if (((InputStack).getItem() == new ItemStack(Items.LAPIS_LAZULI, (int) (1)).getItem())) {
			StackSize = (double) 64;
			InAmount = (double) 9;
			Amount = (double) 1;
			OutputStack = new ItemStack(Blocks.LAPIS_BLOCK, (int) (1));
		}
		if (((InputStack).getItem() == new ItemStack(Items.WATER_BUCKET, (int) (1)).getItem())) {
			StackSize = (double) 64;
			InAmount = (double) 0;
			Amount = (double) 1;
			OutputStack = new ItemStack(Blocks.PACKED_ICE, (int) (1));
		}
		if (((InputStack).getItem() == new ItemStack(Blocks.PACKED_ICE, (int) (1)).getItem())) {
			StackSize = (double) 16;
			InAmount = (double) 1;
			Amount = (double) 1;
			OutputStack = new ItemStack(Items.SNOWBALL, (int) (1));
		}
		if (((InputStack).getItem() == new ItemStack(Items.SNOWBALL, (int) (1)).getItem())) {
			StackSize = (double) 64;
			InAmount = (double) 4;
			Amount = (double) 1;
			OutputStack = new ItemStack(Blocks.SNOW_BLOCK, (int) (1));
		}
		if (((InputStack).getItem() == new ItemStack(Blocks.NETHERRACK, (int) (1)).getItem())) {
			StackSize = (double) 64;
			InAmount = (double) 1;
			Amount = (double) 1;
			OutputStack = new ItemStack(Blocks.COBBLESTONE, (int) (1));
		}
		if (((InputStack).getItem() == new ItemStack(Blocks.COBBLESTONE, (int) (1)).getItem())) {
			StackSize = (double) 64;
			InAmount = (double) 1;
			Amount = (double) 1;
			OutputStack = new ItemStack(Blocks.STONE, (int) (1));
		}
		if (((InputStack).getItem() == new ItemStack(Blocks.STONE, (int) (1)).getItem())) {
			StackSize = (double) 64;
			InAmount = (double) 1;
			Amount = (double) 1;
			OutputStack = new ItemStack(Blocks.SMOOTH_STONE, (int) (1));
		}
		if (((InputStack).getItem() == new ItemStack(Items.QUARTZ, (int) (1)).getItem())) {
			StackSize = (double) 64;
			InAmount = (double) 1;
			Amount = (double) 1;
			OutputStack = new ItemStack(Items.IRON_INGOT, (int) (1));
		}
		if (((InputStack).getItem() == new ItemStack(Blocks.ANCIENT_DEBRIS, (int) (1)).getItem())) {
			StackSize = (double) 64;
			InAmount = (double) 1;
			Amount = (double) 1;
			OutputStack = new ItemStack(Items.NETHERITE_SCRAP, (int) (1));
		}
		if (((InputStack).getItem() == new ItemStack(Blocks.WHEAT, (int) (1)).getItem())) {
			StackSize = (double) 64;
			InAmount = (double) 3;
			Amount = (double) 1;
			OutputStack = new ItemStack(Items.BREAD, (int) (1));
		}
		if (((InputStack).getItem() == new ItemStack(Items.GLOWSTONE_DUST, (int) (1)).getItem())) {
			StackSize = (double) 64;
			InAmount = (double) 9;
			Amount = (double) 1;
			OutputStack = new ItemStack(Blocks.GLOWSTONE, (int) (1));
		}
		if (((InputStack).getItem() == new ItemStack(Items.POTATO, (int) (1)).getItem())) {
			StackSize = (double) 64;
			InAmount = (double) 1;
			Amount = (double) 1;
			OutputStack = new ItemStack(Items.BAKED_POTATO, (int) (1));
		}
		if (((InputStack).getItem() == new ItemStack(Items.PORKCHOP, (int) (1)).getItem())) {
			StackSize = (double) 64;
			InAmount = (double) 1;
			Amount = (double) 1;
			OutputStack = new ItemStack(Items.COOKED_PORKCHOP, (int) (1));
		}
		if (((InputStack).getItem() == new ItemStack(Items.BEEF, (int) (1)).getItem())) {
			StackSize = (double) 64;
			InAmount = (double) 1;
			Amount = (double) 1;
			OutputStack = new ItemStack(Items.COOKED_BEEF, (int) (1));
		}
		if (((InputStack).getItem() == new ItemStack(Items.CHICKEN, (int) (1)).getItem())) {
			StackSize = (double) 64;
			InAmount = (double) 1;
			Amount = (double) 1;
			OutputStack = new ItemStack(Items.COOKED_CHICKEN, (int) (1));
		}
		if (((InputStack).getItem() == new ItemStack(Items.MUTTON, (int) (1)).getItem())) {
			StackSize = (double) 64;
			InAmount = (double) 1;
			Amount = (double) 1;
			OutputStack = new ItemStack(Items.COOKED_MUTTON, (int) (1));
		}
		if (((InputStack).getItem() == new ItemStack(Items.RABBIT, (int) (1)).getItem())) {
			StackSize = (double) 64;
			InAmount = (double) 1;
			Amount = (double) 1;
			OutputStack = new ItemStack(Items.COOKED_RABBIT, (int) (1));
		}
		if (((InputStack).getItem() == new ItemStack(Items.SALMON, (int) (1)).getItem())) {
			StackSize = (double) 64;
			InAmount = (double) 1;
			Amount = (double) 1;
			OutputStack = new ItemStack(Items.COOKED_SALMON, (int) (1));
		}
		if (((InputStack).getItem() == new ItemStack(Items.COD, (int) (1)).getItem())) {
			StackSize = (double) 64;
			InAmount = (double) 1;
			Amount = (double) 1;
			OutputStack = new ItemStack(Items.COOKED_COD, (int) (1));
		}
		if (((((new Object() {
			public ItemStack getItemStack(BlockPos pos, int sltid) {
				AtomicReference<ItemStack> _retval = new AtomicReference<>(ItemStack.EMPTY);
				TileEntity _ent = world.getTileEntity(pos);
				if (_ent != null) {
					_ent.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(capability -> {
						_retval.set(capability.getStackInSlot(sltid).copy());
					});
				}
				return _retval.get();
			}
		}.getItemStack(new BlockPos((int) x, (int) y, (int) z), (int) (1))).getItem() == new ItemStack(Blocks.AIR, (int) (1)).getItem())
				|| (((new Object() {
					public ItemStack getItemStack(BlockPos pos, int sltid) {
						AtomicReference<ItemStack> _retval = new AtomicReference<>(ItemStack.EMPTY);
						TileEntity _ent = world.getTileEntity(pos);
						if (_ent != null) {
							_ent.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(capability -> {
								_retval.set(capability.getStackInSlot(sltid).copy());
							});
						}
						return _retval.get();
					}
				}.getItemStack(new BlockPos((int) x, (int) y, (int) z), (int) (1))).getItem() == (OutputStack).getItem()) && ((new Object() {
					public int getAmount(IWorld world, BlockPos pos, int sltid) {
						AtomicInteger _retval = new AtomicInteger(0);
						TileEntity _ent = world.getTileEntity(pos);
						if (_ent != null) {
							_ent.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(capability -> {
								_retval.set(capability.getStackInSlot(sltid).getCount());
							});
						}
						return _retval.get();
					}
				}.getAmount(world, new BlockPos((int) x, (int) y, (int) z), (int) (1))) <= ((StackSize) - (Amount))))) && ((new Object() {
					public int getAmount(IWorld world, BlockPos pos, int sltid) {
						AtomicInteger _retval = new AtomicInteger(0);
						TileEntity _ent = world.getTileEntity(pos);
						if (_ent != null) {
							_ent.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(capability -> {
								_retval.set(capability.getStackInSlot(sltid).getCount());
							});
						}
						return _retval.get();
					}
				}.getAmount(world, new BlockPos((int) x, (int) y, (int) z), (int) (0))) >= (InAmount)))) {
			{
				TileEntity _ent = world.getTileEntity(new BlockPos((int) x, (int) y, (int) z));
				if (_ent != null) {
					final int _sltid = (int) (1);
					final ItemStack _setstack = (OutputStack);
					_setstack.setCount((int) ((new Object() {
						public int getAmount(IWorld world, BlockPos pos, int sltid) {
							AtomicInteger _retval = new AtomicInteger(0);
							TileEntity _ent = world.getTileEntity(pos);
							if (_ent != null) {
								_ent.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(capability -> {
									_retval.set(capability.getStackInSlot(sltid).getCount());
								});
							}
							return _retval.get();
						}
					}.getAmount(world, new BlockPos((int) x, (int) y, (int) z), (int) (1))) + (Amount)));
					_ent.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(capability -> {
						if (capability instanceof IItemHandlerModifiable) {
							((IItemHandlerModifiable) capability).setStackInSlot(_sltid, _setstack);
						}
					});
				}
			}
			{
				TileEntity _ent = world.getTileEntity(new BlockPos((int) x, (int) y, (int) z));
				if (_ent != null) {
					final int _sltid = (int) (0);
					final int _amount = (int) (InAmount);
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
}
