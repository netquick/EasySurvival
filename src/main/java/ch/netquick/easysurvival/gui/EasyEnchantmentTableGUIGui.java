
package ch.netquick.easysurvival.gui;

import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.IContainerFactory;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.World;
import net.minecraft.util.math.BlockPos;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.item.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.inventory.container.Slot;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Container;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.client.gui.ScreenManager;

import java.util.function.Supplier;
import java.util.Map;
import java.util.HashMap;

import ch.netquick.easysurvival.procedures.EasyEnchantmentTableGUItakeItem9Procedure;
import ch.netquick.easysurvival.procedures.EasyEnchantmentTableGUItakeItem8Procedure;
import ch.netquick.easysurvival.procedures.EasyEnchantmentTableGUItakeItem7Procedure;
import ch.netquick.easysurvival.procedures.EasyEnchantmentTableGUItakeItem6Procedure;
import ch.netquick.easysurvival.procedures.EasyEnchantmentTableGUItakeItem5Procedure;
import ch.netquick.easysurvival.procedures.EasyEnchantmentTableGUItakeItem4Procedure;
import ch.netquick.easysurvival.procedures.EasyEnchantmentTableGUItakeItem3Procedure;
import ch.netquick.easysurvival.procedures.EasyEnchantmentTableGUItakeItem2Procedure;
import ch.netquick.easysurvival.procedures.EasyEnchantmentTableGUItakeItem1Procedure;
import ch.netquick.easysurvival.procedures.EasyEnchantmentTableGUIresetProcedure;
import ch.netquick.easysurvival.EasysurvivalModElements;
import ch.netquick.easysurvival.EasysurvivalMod;

@EasysurvivalModElements.ModElement.Tag
public class EasyEnchantmentTableGUIGui extends EasysurvivalModElements.ModElement {
	public static HashMap guistate = new HashMap();
	private static ContainerType<GuiContainerMod> containerType = null;
	public EasyEnchantmentTableGUIGui(EasysurvivalModElements instance) {
		super(instance, 65);
		elements.addNetworkMessage(ButtonPressedMessage.class, ButtonPressedMessage::buffer, ButtonPressedMessage::new,
				ButtonPressedMessage::handler);
		elements.addNetworkMessage(GUISlotChangedMessage.class, GUISlotChangedMessage::buffer, GUISlotChangedMessage::new,
				GUISlotChangedMessage::handler);
		containerType = new ContainerType<>(new GuiContainerModFactory());
		FMLJavaModLoadingContext.get().getModEventBus().register(new ContainerRegisterHandler());
	}
	private static class ContainerRegisterHandler {
		@SubscribeEvent
		public void registerContainer(RegistryEvent.Register<ContainerType<?>> event) {
			event.getRegistry().register(containerType.setRegistryName("easy_enchantment_table_gui"));
		}
	}
	@OnlyIn(Dist.CLIENT)
	public void initElements() {
		DeferredWorkQueue.runLater(() -> ScreenManager.registerFactory(containerType, EasyEnchantmentTableGUIGuiWindow::new));
	}
	public static class GuiContainerModFactory implements IContainerFactory {
		public GuiContainerMod create(int id, PlayerInventory inv, PacketBuffer extraData) {
			return new GuiContainerMod(id, inv, extraData);
		}
	}

	public static class GuiContainerMod extends Container implements Supplier<Map<Integer, Slot>> {
		World world;
		PlayerEntity entity;
		int x, y, z;
		private IItemHandler internal;
		private Map<Integer, Slot> customSlots = new HashMap<>();
		private boolean bound = false;
		public GuiContainerMod(int id, PlayerInventory inv, PacketBuffer extraData) {
			super(containerType, id);
			this.entity = inv.player;
			this.world = inv.player.world;
			this.internal = new ItemStackHandler(11);
			BlockPos pos = null;
			if (extraData != null) {
				pos = extraData.readBlockPos();
				this.x = pos.getX();
				this.y = pos.getY();
				this.z = pos.getZ();
			}
			if (pos != null) {
				if (extraData.readableBytes() == 1) { // bound to item
					byte hand = extraData.readByte();
					ItemStack itemstack;
					if (hand == 0)
						itemstack = this.entity.getHeldItemMainhand();
					else
						itemstack = this.entity.getHeldItemOffhand();
					itemstack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(capability -> {
						this.internal = capability;
						this.bound = true;
					});
				} else if (extraData.readableBytes() > 1) {
					extraData.readByte(); // drop padding
					Entity entity = world.getEntityByID(extraData.readVarInt());
					if (entity != null)
						entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(capability -> {
							this.internal = capability;
							this.bound = true;
						});
				} else { // might be bound to block
					TileEntity ent = inv.player != null ? inv.player.world.getTileEntity(pos) : null;
					if (ent != null) {
						ent.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(capability -> {
							this.internal = capability;
							this.bound = true;
						});
					}
				}
			}
			this.customSlots.put(0, this.addSlot(new SlotItemHandler(internal, 0, 151, 12) {
				@Override
				public ItemStack onTake(PlayerEntity entity, ItemStack stack) {
					ItemStack retval = super.onTake(entity, stack);
					GuiContainerMod.this.slotChanged(0, 1, 0);
					return retval;
				}

				@Override
				public void onSlotChange(ItemStack a, ItemStack b) {
					super.onSlotChange(a, b);
					GuiContainerMod.this.slotChanged(0, 2, b.getCount() - a.getCount());
				}
			}));
			this.customSlots.put(1, this.addSlot(new SlotItemHandler(internal, 1, 7, 57) {
				@Override
				public ItemStack onTake(PlayerEntity entity, ItemStack stack) {
					ItemStack retval = super.onTake(entity, stack);
					GuiContainerMod.this.slotChanged(1, 1, 0);
					return retval;
				}

				@Override
				public void onSlotChange(ItemStack a, ItemStack b) {
					super.onSlotChange(a, b);
					GuiContainerMod.this.slotChanged(1, 2, b.getCount() - a.getCount());
				}

				@Override
				public boolean isItemValid(ItemStack stack) {
					return false;
				}
			}));
			this.customSlots.put(2, this.addSlot(new SlotItemHandler(internal, 2, 25, 57) {
				@Override
				public ItemStack onTake(PlayerEntity entity, ItemStack stack) {
					ItemStack retval = super.onTake(entity, stack);
					GuiContainerMod.this.slotChanged(2, 1, 0);
					return retval;
				}

				@Override
				public void onSlotChange(ItemStack a, ItemStack b) {
					super.onSlotChange(a, b);
					GuiContainerMod.this.slotChanged(2, 2, b.getCount() - a.getCount());
				}
			}));
			this.customSlots.put(3, this.addSlot(new SlotItemHandler(internal, 3, 43, 57) {
				@Override
				public ItemStack onTake(PlayerEntity entity, ItemStack stack) {
					ItemStack retval = super.onTake(entity, stack);
					GuiContainerMod.this.slotChanged(3, 1, 0);
					return retval;
				}

				@Override
				public void onSlotChange(ItemStack a, ItemStack b) {
					super.onSlotChange(a, b);
					GuiContainerMod.this.slotChanged(3, 2, b.getCount() - a.getCount());
				}
			}));
			this.customSlots.put(4, this.addSlot(new SlotItemHandler(internal, 4, 61, 57) {
				@Override
				public ItemStack onTake(PlayerEntity entity, ItemStack stack) {
					ItemStack retval = super.onTake(entity, stack);
					GuiContainerMod.this.slotChanged(4, 1, 0);
					return retval;
				}

				@Override
				public void onSlotChange(ItemStack a, ItemStack b) {
					super.onSlotChange(a, b);
					GuiContainerMod.this.slotChanged(4, 2, b.getCount() - a.getCount());
				}
			}));
			this.customSlots.put(5, this.addSlot(new SlotItemHandler(internal, 5, 79, 57) {
				@Override
				public ItemStack onTake(PlayerEntity entity, ItemStack stack) {
					ItemStack retval = super.onTake(entity, stack);
					GuiContainerMod.this.slotChanged(5, 1, 0);
					return retval;
				}

				@Override
				public void onSlotChange(ItemStack a, ItemStack b) {
					super.onSlotChange(a, b);
					GuiContainerMod.this.slotChanged(5, 2, b.getCount() - a.getCount());
				}
			}));
			this.customSlots.put(6, this.addSlot(new SlotItemHandler(internal, 6, 97, 57) {
				@Override
				public ItemStack onTake(PlayerEntity entity, ItemStack stack) {
					ItemStack retval = super.onTake(entity, stack);
					GuiContainerMod.this.slotChanged(6, 1, 0);
					return retval;
				}

				@Override
				public void onSlotChange(ItemStack a, ItemStack b) {
					super.onSlotChange(a, b);
					GuiContainerMod.this.slotChanged(6, 2, b.getCount() - a.getCount());
				}
			}));
			this.customSlots.put(7, this.addSlot(new SlotItemHandler(internal, 7, 115, 57) {
				@Override
				public ItemStack onTake(PlayerEntity entity, ItemStack stack) {
					ItemStack retval = super.onTake(entity, stack);
					GuiContainerMod.this.slotChanged(7, 1, 0);
					return retval;
				}

				@Override
				public void onSlotChange(ItemStack a, ItemStack b) {
					super.onSlotChange(a, b);
					GuiContainerMod.this.slotChanged(7, 2, b.getCount() - a.getCount());
				}
			}));
			this.customSlots.put(8, this.addSlot(new SlotItemHandler(internal, 8, 133, 57) {
				@Override
				public ItemStack onTake(PlayerEntity entity, ItemStack stack) {
					ItemStack retval = super.onTake(entity, stack);
					GuiContainerMod.this.slotChanged(8, 1, 0);
					return retval;
				}

				@Override
				public void onSlotChange(ItemStack a, ItemStack b) {
					super.onSlotChange(a, b);
					GuiContainerMod.this.slotChanged(8, 2, b.getCount() - a.getCount());
				}
			}));
			this.customSlots.put(9, this.addSlot(new SlotItemHandler(internal, 9, 151, 57) {
				@Override
				public ItemStack onTake(PlayerEntity entity, ItemStack stack) {
					ItemStack retval = super.onTake(entity, stack);
					GuiContainerMod.this.slotChanged(9, 1, 0);
					return retval;
				}

				@Override
				public void onSlotChange(ItemStack a, ItemStack b) {
					super.onSlotChange(a, b);
					GuiContainerMod.this.slotChanged(9, 2, b.getCount() - a.getCount());
				}
			}));
			this.customSlots.put(10, this.addSlot(new SlotItemHandler(internal, 10, 151, 35) {
				@Override
				public ItemStack onTake(PlayerEntity entity, ItemStack stack) {
					ItemStack retval = super.onTake(entity, stack);
					GuiContainerMod.this.slotChanged(10, 1, 0);
					return retval;
				}

				@Override
				public void onSlotChange(ItemStack a, ItemStack b) {
					super.onSlotChange(a, b);
					GuiContainerMod.this.slotChanged(10, 2, b.getCount() - a.getCount());
				}

				@Override
				public boolean isItemValid(ItemStack stack) {
					return (new ItemStack(Items.LAPIS_LAZULI, (int) (1)).getItem() == stack.getItem());
				}
			}));
			int si;
			int sj;
			for (si = 0; si < 3; ++si)
				for (sj = 0; sj < 9; ++sj)
					this.addSlot(new Slot(inv, sj + (si + 1) * 9, 0 + 8 + sj * 18, 0 + 84 + si * 18));
			for (si = 0; si < 9; ++si)
				this.addSlot(new Slot(inv, si, 0 + 8 + si * 18, 0 + 142));
		}

		public Map<Integer, Slot> get() {
			return customSlots;
		}

		@Override
		public boolean canInteractWith(PlayerEntity player) {
			return true;
		}

		@Override
		public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
			ItemStack itemstack = ItemStack.EMPTY;
			Slot slot = (Slot) this.inventorySlots.get(index);
			if (slot != null && slot.getHasStack()) {
				ItemStack itemstack1 = slot.getStack();
				itemstack = itemstack1.copy();
				if (index < 11) {
					if (!this.mergeItemStack(itemstack1, 11, this.inventorySlots.size(), true)) {
						return ItemStack.EMPTY;
					}
					slot.onSlotChange(itemstack1, itemstack);
				} else if (!this.mergeItemStack(itemstack1, 0, 11, false)) {
					if (index < 11 + 27) {
						if (!this.mergeItemStack(itemstack1, 11 + 27, this.inventorySlots.size(), true)) {
							return ItemStack.EMPTY;
						}
					} else {
						if (!this.mergeItemStack(itemstack1, 11, 11 + 27, false)) {
							return ItemStack.EMPTY;
						}
					}
					return ItemStack.EMPTY;
				}
				if (itemstack1.getCount() == 0) {
					slot.putStack(ItemStack.EMPTY);
				} else {
					slot.onSlotChanged();
				}
				if (itemstack1.getCount() == itemstack.getCount()) {
					return ItemStack.EMPTY;
				}
				slot.onTake(playerIn, itemstack1);
			}
			return itemstack;
		}

		@Override /**
					 * Merges provided ItemStack with the first avaliable one in the
					 * container/player inventor between minIndex (included) and maxIndex
					 * (excluded). Args : stack, minIndex, maxIndex, negativDirection. /!\ the
					 * Container implementation do not check if the item is valid for the slot
					 */
		protected boolean mergeItemStack(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection) {
			boolean flag = false;
			int i = startIndex;
			if (reverseDirection) {
				i = endIndex - 1;
			}
			if (stack.isStackable()) {
				while (!stack.isEmpty()) {
					if (reverseDirection) {
						if (i < startIndex) {
							break;
						}
					} else if (i >= endIndex) {
						break;
					}
					Slot slot = this.inventorySlots.get(i);
					ItemStack itemstack = slot.getStack();
					if (slot.isItemValid(itemstack) && !itemstack.isEmpty() && areItemsAndTagsEqual(stack, itemstack)) {
						int j = itemstack.getCount() + stack.getCount();
						int maxSize = Math.min(slot.getSlotStackLimit(), stack.getMaxStackSize());
						if (j <= maxSize) {
							stack.setCount(0);
							itemstack.setCount(j);
							slot.putStack(itemstack);
							flag = true;
						} else if (itemstack.getCount() < maxSize) {
							stack.shrink(maxSize - itemstack.getCount());
							itemstack.setCount(maxSize);
							slot.putStack(itemstack);
							flag = true;
						}
					}
					if (reverseDirection) {
						--i;
					} else {
						++i;
					}
				}
			}
			if (!stack.isEmpty()) {
				if (reverseDirection) {
					i = endIndex - 1;
				} else {
					i = startIndex;
				}
				while (true) {
					if (reverseDirection) {
						if (i < startIndex) {
							break;
						}
					} else if (i >= endIndex) {
						break;
					}
					Slot slot1 = this.inventorySlots.get(i);
					ItemStack itemstack1 = slot1.getStack();
					if (itemstack1.isEmpty() && slot1.isItemValid(stack)) {
						if (stack.getCount() > slot1.getSlotStackLimit()) {
							slot1.putStack(stack.split(slot1.getSlotStackLimit()));
						} else {
							slot1.putStack(stack.split(stack.getCount()));
						}
						slot1.onSlotChanged();
						flag = true;
						break;
					}
					if (reverseDirection) {
						--i;
					} else {
						++i;
					}
				}
			}
			return flag;
		}

		@Override
		public void onContainerClosed(PlayerEntity playerIn) {
			super.onContainerClosed(playerIn);
			if (!bound && (playerIn instanceof ServerPlayerEntity)) {
				if (!playerIn.isAlive() || playerIn instanceof ServerPlayerEntity && ((ServerPlayerEntity) playerIn).hasDisconnected()) {
					for (int j = 0; j < internal.getSlots(); ++j) {
						playerIn.dropItem(internal.extractItem(j, internal.getStackInSlot(j).getCount(), false), false);
					}
				} else {
					for (int i = 0; i < internal.getSlots(); ++i) {
						playerIn.inventory.placeItemBackInInventory(playerIn.world,
								internal.extractItem(i, internal.getStackInSlot(i).getCount(), false));
					}
				}
			}
		}

		private void slotChanged(int slotid, int ctype, int meta) {
			if (this.world != null && this.world.isRemote()) {
				EasysurvivalMod.PACKET_HANDLER.sendToServer(new GUISlotChangedMessage(slotid, x, y, z, ctype, meta));
				handleSlotAction(entity, slotid, ctype, meta, x, y, z);
			}
		}
	}

	public static class ButtonPressedMessage {
		int buttonID, x, y, z;
		public ButtonPressedMessage(PacketBuffer buffer) {
			this.buttonID = buffer.readInt();
			this.x = buffer.readInt();
			this.y = buffer.readInt();
			this.z = buffer.readInt();
		}

		public ButtonPressedMessage(int buttonID, int x, int y, int z) {
			this.buttonID = buttonID;
			this.x = x;
			this.y = y;
			this.z = z;
		}

		public static void buffer(ButtonPressedMessage message, PacketBuffer buffer) {
			buffer.writeInt(message.buttonID);
			buffer.writeInt(message.x);
			buffer.writeInt(message.y);
			buffer.writeInt(message.z);
		}

		public static void handler(ButtonPressedMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
			NetworkEvent.Context context = contextSupplier.get();
			context.enqueueWork(() -> {
				PlayerEntity entity = context.getSender();
				int buttonID = message.buttonID;
				int x = message.x;
				int y = message.y;
				int z = message.z;
				handleButtonAction(entity, buttonID, x, y, z);
			});
			context.setPacketHandled(true);
		}
	}

	public static class GUISlotChangedMessage {
		int slotID, x, y, z, changeType, meta;
		public GUISlotChangedMessage(int slotID, int x, int y, int z, int changeType, int meta) {
			this.slotID = slotID;
			this.x = x;
			this.y = y;
			this.z = z;
			this.changeType = changeType;
			this.meta = meta;
		}

		public GUISlotChangedMessage(PacketBuffer buffer) {
			this.slotID = buffer.readInt();
			this.x = buffer.readInt();
			this.y = buffer.readInt();
			this.z = buffer.readInt();
			this.changeType = buffer.readInt();
			this.meta = buffer.readInt();
		}

		public static void buffer(GUISlotChangedMessage message, PacketBuffer buffer) {
			buffer.writeInt(message.slotID);
			buffer.writeInt(message.x);
			buffer.writeInt(message.y);
			buffer.writeInt(message.z);
			buffer.writeInt(message.changeType);
			buffer.writeInt(message.meta);
		}

		public static void handler(GUISlotChangedMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
			NetworkEvent.Context context = contextSupplier.get();
			context.enqueueWork(() -> {
				PlayerEntity entity = context.getSender();
				int slotID = message.slotID;
				int changeType = message.changeType;
				int meta = message.meta;
				int x = message.x;
				int y = message.y;
				int z = message.z;
				handleSlotAction(entity, slotID, changeType, meta, x, y, z);
			});
			context.setPacketHandled(true);
		}
	}
	static void handleButtonAction(PlayerEntity entity, int buttonID, int x, int y, int z) {
		World world = entity.world;
		// security measure to prevent arbitrary chunk generation
		if (!world.isBlockLoaded(new BlockPos(x, y, z)))
			return;
	}

	private static void handleSlotAction(PlayerEntity entity, int slotID, int changeType, int meta, int x, int y, int z) {
		World world = entity.world;
		// security measure to prevent arbitrary chunk generation
		if (!world.isBlockLoaded(new BlockPos(x, y, z)))
			return;
		if (slotID == 0 && changeType == 1) {
			{
				Map<String, Object> $_dependencies = new HashMap<>();
				$_dependencies.put("x", x);
				$_dependencies.put("y", y);
				$_dependencies.put("z", z);
				$_dependencies.put("world", world);
				EasyEnchantmentTableGUIresetProcedure.executeProcedure($_dependencies);
			}
		}
		if (slotID == 0 && changeType == 2) {
			int amount = meta;
			{
				Map<String, Object> $_dependencies = new HashMap<>();
				$_dependencies.put("x", x);
				$_dependencies.put("y", y);
				$_dependencies.put("z", z);
				$_dependencies.put("world", world);
				EasyEnchantmentTableGUIresetProcedure.executeProcedure($_dependencies);
			}
		}
		if (slotID == 1 && changeType == 1) {
			{
				Map<String, Object> $_dependencies = new HashMap<>();
				$_dependencies.put("x", x);
				$_dependencies.put("y", y);
				$_dependencies.put("z", z);
				$_dependencies.put("world", world);
				EasyEnchantmentTableGUItakeItem1Procedure.executeProcedure($_dependencies);
			}
		}
		if (slotID == 1 && changeType == 2) {
			int amount = meta;
			{
				Map<String, Object> $_dependencies = new HashMap<>();
				$_dependencies.put("x", x);
				$_dependencies.put("y", y);
				$_dependencies.put("z", z);
				$_dependencies.put("world", world);
				EasyEnchantmentTableGUItakeItem1Procedure.executeProcedure($_dependencies);
			}
		}
		if (slotID == 2 && changeType == 1) {
			{
				Map<String, Object> $_dependencies = new HashMap<>();
				$_dependencies.put("x", x);
				$_dependencies.put("y", y);
				$_dependencies.put("z", z);
				$_dependencies.put("world", world);
				EasyEnchantmentTableGUItakeItem2Procedure.executeProcedure($_dependencies);
			}
		}
		if (slotID == 2 && changeType == 2) {
			int amount = meta;
			{
				Map<String, Object> $_dependencies = new HashMap<>();
				$_dependencies.put("x", x);
				$_dependencies.put("y", y);
				$_dependencies.put("z", z);
				$_dependencies.put("world", world);
				EasyEnchantmentTableGUItakeItem2Procedure.executeProcedure($_dependencies);
			}
		}
		if (slotID == 3 && changeType == 1) {
			{
				Map<String, Object> $_dependencies = new HashMap<>();
				$_dependencies.put("x", x);
				$_dependencies.put("y", y);
				$_dependencies.put("z", z);
				$_dependencies.put("world", world);
				EasyEnchantmentTableGUItakeItem3Procedure.executeProcedure($_dependencies);
			}
		}
		if (slotID == 3 && changeType == 2) {
			int amount = meta;
			{
				Map<String, Object> $_dependencies = new HashMap<>();
				$_dependencies.put("x", x);
				$_dependencies.put("y", y);
				$_dependencies.put("z", z);
				$_dependencies.put("world", world);
				EasyEnchantmentTableGUItakeItem3Procedure.executeProcedure($_dependencies);
			}
		}
		if (slotID == 4 && changeType == 1) {
			{
				Map<String, Object> $_dependencies = new HashMap<>();
				$_dependencies.put("x", x);
				$_dependencies.put("y", y);
				$_dependencies.put("z", z);
				$_dependencies.put("world", world);
				EasyEnchantmentTableGUItakeItem4Procedure.executeProcedure($_dependencies);
			}
		}
		if (slotID == 4 && changeType == 2) {
			int amount = meta;
			{
				Map<String, Object> $_dependencies = new HashMap<>();
				$_dependencies.put("x", x);
				$_dependencies.put("y", y);
				$_dependencies.put("z", z);
				$_dependencies.put("world", world);
				EasyEnchantmentTableGUItakeItem4Procedure.executeProcedure($_dependencies);
			}
		}
		if (slotID == 5 && changeType == 1) {
			{
				Map<String, Object> $_dependencies = new HashMap<>();
				$_dependencies.put("x", x);
				$_dependencies.put("y", y);
				$_dependencies.put("z", z);
				$_dependencies.put("world", world);
				EasyEnchantmentTableGUItakeItem5Procedure.executeProcedure($_dependencies);
			}
		}
		if (slotID == 5 && changeType == 2) {
			int amount = meta;
			{
				Map<String, Object> $_dependencies = new HashMap<>();
				$_dependencies.put("x", x);
				$_dependencies.put("y", y);
				$_dependencies.put("z", z);
				$_dependencies.put("world", world);
				EasyEnchantmentTableGUItakeItem5Procedure.executeProcedure($_dependencies);
			}
		}
		if (slotID == 6 && changeType == 1) {
			{
				Map<String, Object> $_dependencies = new HashMap<>();
				$_dependencies.put("x", x);
				$_dependencies.put("y", y);
				$_dependencies.put("z", z);
				$_dependencies.put("world", world);
				EasyEnchantmentTableGUItakeItem6Procedure.executeProcedure($_dependencies);
			}
		}
		if (slotID == 6 && changeType == 2) {
			int amount = meta;
			{
				Map<String, Object> $_dependencies = new HashMap<>();
				$_dependencies.put("x", x);
				$_dependencies.put("y", y);
				$_dependencies.put("z", z);
				$_dependencies.put("world", world);
				EasyEnchantmentTableGUItakeItem6Procedure.executeProcedure($_dependencies);
			}
		}
		if (slotID == 7 && changeType == 1) {
			{
				Map<String, Object> $_dependencies = new HashMap<>();
				$_dependencies.put("x", x);
				$_dependencies.put("y", y);
				$_dependencies.put("z", z);
				$_dependencies.put("world", world);
				EasyEnchantmentTableGUItakeItem7Procedure.executeProcedure($_dependencies);
			}
		}
		if (slotID == 7 && changeType == 2) {
			int amount = meta;
			{
				Map<String, Object> $_dependencies = new HashMap<>();
				$_dependencies.put("x", x);
				$_dependencies.put("y", y);
				$_dependencies.put("z", z);
				$_dependencies.put("world", world);
				EasyEnchantmentTableGUItakeItem7Procedure.executeProcedure($_dependencies);
			}
		}
		if (slotID == 8 && changeType == 1) {
			{
				Map<String, Object> $_dependencies = new HashMap<>();
				$_dependencies.put("x", x);
				$_dependencies.put("y", y);
				$_dependencies.put("z", z);
				$_dependencies.put("world", world);
				EasyEnchantmentTableGUItakeItem8Procedure.executeProcedure($_dependencies);
			}
		}
		if (slotID == 8 && changeType == 2) {
			int amount = meta;
			{
				Map<String, Object> $_dependencies = new HashMap<>();
				$_dependencies.put("x", x);
				$_dependencies.put("y", y);
				$_dependencies.put("z", z);
				$_dependencies.put("world", world);
				EasyEnchantmentTableGUItakeItem8Procedure.executeProcedure($_dependencies);
			}
		}
		if (slotID == 9 && changeType == 1) {
			{
				Map<String, Object> $_dependencies = new HashMap<>();
				$_dependencies.put("x", x);
				$_dependencies.put("y", y);
				$_dependencies.put("z", z);
				$_dependencies.put("world", world);
				EasyEnchantmentTableGUItakeItem9Procedure.executeProcedure($_dependencies);
			}
		}
		if (slotID == 9 && changeType == 2) {
			int amount = meta;
			{
				Map<String, Object> $_dependencies = new HashMap<>();
				$_dependencies.put("x", x);
				$_dependencies.put("y", y);
				$_dependencies.put("z", z);
				$_dependencies.put("world", world);
				EasyEnchantmentTableGUItakeItem9Procedure.executeProcedure($_dependencies);
			}
		}
		if (slotID == 10 && changeType == 1) {
			{
				Map<String, Object> $_dependencies = new HashMap<>();
				$_dependencies.put("x", x);
				$_dependencies.put("y", y);
				$_dependencies.put("z", z);
				$_dependencies.put("world", world);
				EasyEnchantmentTableGUIresetProcedure.executeProcedure($_dependencies);
			}
		}
		if (slotID == 10 && changeType == 2) {
			int amount = meta;
			{
				Map<String, Object> $_dependencies = new HashMap<>();
				$_dependencies.put("x", x);
				$_dependencies.put("y", y);
				$_dependencies.put("z", z);
				$_dependencies.put("world", world);
				EasyEnchantmentTableGUIresetProcedure.executeProcedure($_dependencies);
			}
		}
	}
}
