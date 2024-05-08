package turniplabs.examplemod;

import net.fabricmc.api.ModInitializer;
import net.minecraft.client.render.item.model.ItemModel;
import net.minecraft.client.render.item.model.ItemModelStandard;
import net.minecraft.client.render.stitcher.TextureRegistry;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemFood;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.helper.ItemBuilder;
import turniplabs.halplibe.util.GameStartEntrypoint;


public class ExampleMod implements ModInitializer, GameStartEntrypoint {
    public static final String MOD_ID = "examplemod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static Item bamboo;
	public static Item potato;
	public static Item potatoBaked;
	public static Item carrotStick;
    @Override
    public void onInitialize() {
        LOGGER.info("ExampleMod initialized.");
    }

	@Override
	public void beforeGameStart() {
		// Vanilla BTA Items range from 16384 to about 16600 with music discs occupying the range 18384 to 18395,
		// and items ids can only exist within the 16384 to 32768 range
		// you need to make sure to pick ids that aren't being used by the vanilla game
		// or other mods, a good way to deal with the potential of conflicting ids
		// is to make your ids user configurable
		int startingItemId = 20000;

		bamboo = new ItemBuilder(MOD_ID).build(new Item("bamboo", startingItemId++));
		potato = new ItemBuilder(MOD_ID).build(new ItemFood("potato", startingItemId++, 2, 10, false, 64));
		potatoBaked = new ItemBuilder(MOD_ID).build(new ItemFood("baked.potato", startingItemId++, 6, 24, false, 8));
		carrotStick = new ItemBuilder(MOD_ID)
			.setItemModel(item -> {
				ItemModelStandard model = new ItemModelStandard(item, null);
				model.icon = TextureRegistry.getTexture("examplemod:item/carrot_on_a_stick");
				return model;
			})
			.build(new Item("stick.carrot", startingItemId++));
	}

	@Override
	public void afterGameStart() {

	}

}
