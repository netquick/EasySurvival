
package ch.netquick.easysurvival.item;

import net.minecraftforge.registries.ObjectHolder;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.World;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.Hand;
import net.minecraft.util.Direction;
import net.minecraft.util.ActionResult;
import net.minecraft.network.PacketBuffer;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.item.Rarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Item;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.Container;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.block.BlockState;

import javax.annotation.Nullable;
import javax.annotation.Nonnull;

import java.util.Map;
import java.util.HashMap;

import io.netty.buffer.Unpooled;

import ch.netquick.easysurvival.procedures.PortableCraftingTableRightClickedInAirProcedure;
import ch.netquick.easysurvival.gui.PortableCraftingTableGUIGuiWindow;
import ch.netquick.easysurvival.gui.PortableCraftingTableGUIGui;
import ch.netquick.easysurvival.EasysurvivalModElements;

@EasysurvivalModElements.ModElement.Tag
public class PortableCraftingTableItem extends EasysurvivalModElements.ModElement {
	@ObjectHolder("easysurvival:portable_crafting_table")
	public static final Item block = null;
	public PortableCraftingTableItem(EasysurvivalModElements instance) {
		super(instance, 102);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public void onItemDropped(ItemTossEvent event) {
		if (event.getEntityItem().getItem().getItem() == block) {
			if (Minecraft.getInstance().currentScreen instanceof PortableCraftingTableGUIGuiWindow) {
				Minecraft.getInstance().player.closeScreen();
			}
		}
	}

	@Override
	public void initElements() {
		elements.items.add(() -> new ItemCustom());
	}
	public static class ItemCustom extends Item {
		public ItemCustom() {
			super(new Item.Properties().group(ItemGroup.MISC).maxStackSize(1).rarity(Rarity.COMMON));
			setRegistryName("portable_crafting_table");
		}

		@Override
		public int getItemEnchantability() {
			return 0;
		}

		@Override
		public int getUseDuration(ItemStack itemstack) {
			return 0;
		}

		@Override
		public float getDestroySpeed(ItemStack par1ItemStack, BlockState par2Block) {
			return 1F;
		}

		@Override
		public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity entity, Hand hand) {
			ActionResult<ItemStack> ar = super.onItemRightClick(world, entity, hand);
			ItemStack itemstack = ar.getResult();
			double x = entity.getPosX();
			double y = entity.getPosY();
			double z = entity.getPosZ();
			if (entity instanceof ServerPlayerEntity) {
				NetworkHooks.openGui((ServerPlayerEntity) entity, new INamedContainerProvider() {
					@Override
					public ITextComponent getDisplayName() {
						return new StringTextComponent("Portable Crafting Table");
					}

					@Override
					public Container createMenu(int id, PlayerInventory inventory, PlayerEntity player) {
						PacketBuffer packetBuffer = new PacketBuffer(Unpooled.buffer());
						packetBuffer.writeBlockPos(new BlockPos(x, y, z));
						packetBuffer.writeByte(hand == Hand.MAIN_HAND ? 0 : 1);
						return new PortableCraftingTableGUIGui.GuiContainerMod(id, inventory, packetBuffer);
					}
				}, buf -> {
					buf.writeBlockPos(new BlockPos(x, y, z));
					buf.writeByte(hand == Hand.MAIN_HAND ? 0 : 1);
				});
			}
			{
				Map<String, Object> $_dependencies = new HashMap<>();
				$_dependencies.put("entity", entity);
				$_dependencies.put("x", x);
				$_dependencies.put("y", y);
				$_dependencies.put("z", z);
				$_dependencies.put("world", world);
				PortableCraftingTableRightClickedInAirProcedure.executeProcedure($_dependencies);
			}
			return ar;
		}

		@Override
		public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT compound) {
			return new InventoryCapability();
		}

		@Override
		public CompoundNBT getShareTag(ItemStack stack) {
			CompoundNBT nbt = super.getShareTag(stack);
			if (nbt != null)
				stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)
						.ifPresent(capability -> nbt.put("Inventory", ((ItemStackHandler) capability).serializeNBT()));
			return nbt;
		}

		@Override
		public void readShareTag(ItemStack stack, @Nullable CompoundNBT nbt) {
			super.readShareTag(stack, nbt);
			if (nbt != null)
				stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)
						.ifPresent(capability -> ((ItemStackHandler) capability).deserializeNBT((CompoundNBT) nbt.get("Inventory")));
		}
	}

	private static class InventoryCapability implements ICapabilitySerializable<CompoundNBT> {
		private final LazyOptional<ItemStackHandler> inventory = LazyOptional.of(this::createItemHandler);
		@Override
		public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction side) {
			return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? this.inventory.cast() : LazyOptional.empty();
		}

		@Override
		public CompoundNBT serializeNBT() {
			return getItemHandler().serializeNBT();
		}

		@Override
		public void deserializeNBT(CompoundNBT nbt) {
			getItemHandler().deserializeNBT(nbt);
		}

		private ItemStackHandler createItemHandler() {
			return new ItemStackHandler(10) {
				@Override
				public int getSlotLimit(int slot) {
					return 64;
				}

				@Override
				public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
					return stack.getItem() != block;
				}
			};
		}

		private ItemStackHandler getItemHandler() {
			return inventory.orElseThrow(RuntimeException::new);
		}
	}
}
