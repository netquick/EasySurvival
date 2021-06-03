package ch.netquick.easysurvival.procedures;

@EasysurvivalModElements.ModElement.Tag
public class SpawnerWitherSkelettUpdateTickProcedure extends EasysurvivalModElements.ModElement {

	public SpawnerWitherSkelettUpdateTickProcedure(EasysurvivalModElements instance) {
		super(instance, 111);

	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("x") == null) {
			if (!dependencies.containsKey("x"))
				EasysurvivalMod.LOGGER.warn("Failed to load dependency x for procedure SpawnerWitherSkelettUpdateTick!");
			return;
		}
		if (dependencies.get("y") == null) {
			if (!dependencies.containsKey("y"))
				EasysurvivalMod.LOGGER.warn("Failed to load dependency y for procedure SpawnerWitherSkelettUpdateTick!");
			return;
		}
		if (dependencies.get("z") == null) {
			if (!dependencies.containsKey("z"))
				EasysurvivalMod.LOGGER.warn("Failed to load dependency z for procedure SpawnerWitherSkelettUpdateTick!");
			return;
		}
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				EasysurvivalMod.LOGGER.warn("Failed to load dependency world for procedure SpawnerWitherSkelettUpdateTick!");
			return;
		}

		double x = dependencies.get("x") instanceof Integer ? (int) dependencies.get("x") : (double) dependencies.get("x");
		double y = dependencies.get("y") instanceof Integer ? (int) dependencies.get("y") : (double) dependencies.get("y");
		double z = dependencies.get("z") instanceof Integer ? (int) dependencies.get("z") : (double) dependencies.get("z");
		IWorld world = (IWorld) dependencies.get("world");

		if (((world.getWorldInfo().getGameRulesInstance().getBoolean(SpawnersActiveGameRule.gamerule)) == (true))) {
			if (world instanceof ServerWorld) {
				Entity entityToSpawn = new WitherSkeletonEntity(EntityType.WITHER_SKELETON, (World) world);
				entityToSpawn.setLocationAndAngles(x, (y + 1), z, world.getRandom().nextFloat() * 360F, 0);

				if (entityToSpawn instanceof MobEntity)
					((MobEntity) entityToSpawn).onInitialSpawn((ServerWorld) world, world.getDifficultyForLocation(entityToSpawn.getPosition()),
							SpawnReason.MOB_SUMMONED, (ILivingEntityData) null, (CompoundNBT) null);

				world.addEntity(entityToSpawn);
			}
		}

	}

}
