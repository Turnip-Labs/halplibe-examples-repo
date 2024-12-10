package turniplabs.examplemod.item;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.animal.MobPig;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.phys.Vec3;
import net.minecraft.core.world.World;

public class ItemCarrotStick extends Item {
	public ItemCarrotStick(String name, String namespaceId, int id) {
		super(name, namespaceId, id);
	}
	@Override
	public void inventoryTick(ItemStack itemstack, World world, Entity entity, int i, boolean flag) {
		if (!(entity instanceof Player)) return;
		if (((Player) entity).getHeldItem() == null) return;
		if (((Player) entity).getHeldItem().getItem() != this) return;
		if (!(entity.vehicle instanceof MobPig)) return;
		MobPig pig = (MobPig) entity.vehicle;
		Vec3 looking = entity.getLookAngle();
		int x = (int) (entity.x + looking.x * 5);
		int y = (int) (entity.y + looking.y * 5);
		int z = (int) (entity.z + looking.z * 5);
		pig.setPathToEntity(world.getEntityPathToXYZ(pig, x, y, z, 20));
		pig.yRot = entity.yRot;
	}
}
