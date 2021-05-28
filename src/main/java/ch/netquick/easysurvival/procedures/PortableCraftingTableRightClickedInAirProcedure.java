package ch.netquick.easysurvival.procedures;

@EasysurvivalModElements.ModElement.Tag
public class PortableCraftingTableRightClickedInAirProcedure extends EasysurvivalModElements.ModElement {

	public PortableCraftingTableRightClickedInAirProcedure(EasysurvivalModElements instance) {
		super(instance, 104);

	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				EasysurvivalMod.LOGGER.warn("Failed to load dependency entity for procedure PortableCraftingTableRightClickedInAir!");
			return;
		}
		if (dependencies.get("x") == null) {
			if (!dependencies.containsKey("x"))
				EasysurvivalMod.LOGGER.warn("Failed to load dependency x for procedure PortableCraftingTableRightClickedInAir!");
			return;
		}
		if (dependencies.get("y") == null) {
			if (!dependencies.containsKey("y"))
				EasysurvivalMod.LOGGER.warn("Failed to load dependency y for procedure PortableCraftingTableRightClickedInAir!");
			return;
		}
		if (dependencies.get("z") == null) {
			if (!dependencies.containsKey("z"))
				EasysurvivalMod.LOGGER.warn("Failed to load dependency z for procedure PortableCraftingTableRightClickedInAir!");
			return;
		}
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				EasysurvivalMod.LOGGER.warn("Failed to load dependency world for procedure PortableCraftingTableRightClickedInAir!");
			return;
		}

		Entity entity = (Entity) dependencies.get("entity");
		double x = dependencies.get("x") instanceof Integer ? (int) dependencies.get("x") : (double) dependencies.get("x");
		double y = dependencies.get("y") instanceof Integer ? (int) dependencies.get("y") : (double) dependencies.get("y");
		double z = dependencies.get("z") instanceof Integer ? (int) dependencies.get("z") : (double) dependencies.get("z");
		IWorld world = (IWorld) dependencies.get("world");

		{
			Entity _ent = entity;
			if (_ent instanceof ServerPlayerEntity) {
				BlockPos _bpos = new BlockPos((int) x, (int) y, (int) z);
				NetworkHooks.openGui((ServerPlayerEntity) _ent, new INamedContainerProvider() {
					@Override
					public ITextComponent getDisplayName() {
						return new StringTextComponent("PortableCraftingTableGUI");
					}

					@Override
					public Container createMenu(int id, PlayerInventory inventory, PlayerEntity player) {
						return new PortableCraftingTableGUIGui.GuiContainerMod(id, inventory,
								new PacketBuffer(Unpooled.buffer()).writeBlockPos(_bpos));
					}
				}, _bpos);
			}
		}

	}

}
