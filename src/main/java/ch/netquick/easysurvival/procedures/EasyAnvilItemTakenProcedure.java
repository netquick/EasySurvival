package ch.netquick.easysurvival.procedures;

@EasysurvivalModElements.ModElement.Tag
public class EasyAnvilItemTakenProcedure extends EasysurvivalModElements.ModElement {

	public EasyAnvilItemTakenProcedure(EasysurvivalModElements instance) {
		super(instance, 121);

	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				EasysurvivalMod.LOGGER.warn("Failed to load dependency entity for procedure EasyAnvilItemTaken!");
			return;
		}

		Entity entity = (Entity) dependencies.get("entity");

		{
			Entity _ent = entity;
			if (_ent instanceof ServerPlayerEntity) {
				Container _current = ((ServerPlayerEntity) _ent).openContainer;
				if (_current instanceof Supplier) {
					Object invobj = ((Supplier) _current).get();
					if (invobj instanceof Map) {
						((Slot) ((Map) invobj).get((int) (0))).decrStackSize((int) (1));
						_current.detectAndSendChanges();
					}
				}
			}
		}
		{
			Entity _ent = entity;
			if (_ent instanceof ServerPlayerEntity) {
				Container _current = ((ServerPlayerEntity) _ent).openContainer;
				if (_current instanceof Supplier) {
					Object invobj = ((Supplier) _current).get();
					if (invobj instanceof Map) {
						((Slot) ((Map) invobj).get((int) (1))).decrStackSize((int) (1));
						_current.detectAndSendChanges();
					}
				}
			}
		}

	}

}
