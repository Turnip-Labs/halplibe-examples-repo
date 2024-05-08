package turniplabs.examplemod.item;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.animal.EntityPig;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.phys.Vec3d;
import net.minecraft.core.world.World;

public class ItemCarrotStick extends Item {
	public ItemCarrotStick(String name, int id) {
		super(name, id);
	}
	@Override
	public void inventoryTick(ItemStack itemstack, World world, Entity entity, int i, boolean flag) {
		if (!(entity instanceof EntityPlayer)) return;
		if (((EntityPlayer) entity).getHeldItem() == null) return;
		if (((EntityPlayer) entity).getHeldItem().getItem() != this) return;
		if (!(entity.vehicle instanceof EntityPig)) return;
		EntityPig pig = (EntityPig) entity.vehicle;
		Vec3d looking = entity.getLookAngle();
		int x = (int) (entity.x + looking.xCoord * 5);
		int y = (int) (entity.y + looking.yCoord * 5);
		int z = (int) (entity.z + looking.zCoord * 5);
		pig.setPathToEntity(world.getEntityPathToXYZ(pig, x, y, z, 20));
		pig.yRot = entity.yRot;
	}
}
