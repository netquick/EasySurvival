package ch.netquick.easysurvival.procedures;

@EasysurvivalModElements.ModElement.Tag
public class EasyAnvilItemResetProcedure extends EasysurvivalModElements.ModElement {

	public EasyAnvilItemResetProcedure(EasysurvivalModElements instance) {
		super(instance, 123);

	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				EasysurvivalMod.LOGGER.warn("Failed to load dependency entity for procedure EasyAnvilItemReset!");
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
						((Slot) ((Map) invobj).get((int) (2))).decrStackSize((int) (1));
						_current.detectAndSendChanges();
					}
				}
			}
		}

	}

}
