package ch.netquick.easysurvival.world;

import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import net.minecraft.world.GameRules;

import java.lang.reflect.Method;

import ch.netquick.easysurvival.EasysurvivalModElements;

@EasysurvivalModElements.ModElement.Tag
public class SpawnersActiveGameRule extends EasysurvivalModElements.ModElement {
	public static final GameRules.RuleKey<GameRules.BooleanValue> gamerule = GameRules.register("spawnersActive", GameRules.Category.SPAWNING,
			create(false));
	public SpawnersActiveGameRule(EasysurvivalModElements instance) {
		super(instance, 89);
	}

	public static GameRules.RuleType<GameRules.BooleanValue> create(boolean defaultValue) {
		try {
			Method createGameruleMethod = ObfuscationReflectionHelper.findMethod(GameRules.BooleanValue.class, "func_223568_b", boolean.class);
			createGameruleMethod.setAccessible(true);
			return (GameRules.RuleType<GameRules.BooleanValue>) createGameruleMethod.invoke(null, defaultValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
