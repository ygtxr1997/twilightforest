package twilightforest.structures.courtyard;

import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;
import twilightforest.TFFeature;
import twilightforest.TwilightForestMod;
import twilightforest.structures.MossyCobbleTemplateProcessor;
import twilightforest.structures.StructureTFComponent;

import java.util.Random;

public class ComponentNagaCourtyardWallCorner extends StructureTFComponent {
    private static final ResourceLocation WALL = new ResourceLocation(TwilightForestMod.ID, "courtyard/courtyard_wall_corner");
    private static final ResourceLocation WALL_DECAYED = new ResourceLocation(TwilightForestMod.ID, "courtyard/courtyard_wall_corner_decayed");

    @SuppressWarnings({"WeakerAccess", "unused"})
    public ComponentNagaCourtyardWallCorner() {
        super();
    }

    @SuppressWarnings("WeakerAccess")
    public ComponentNagaCourtyardWallCorner(TFFeature feature, int i, int x, int y, int z, Rotation rotation) {
        super(feature, i);
        this.rotation = rotation;
        this.boundingBox = new StructureBoundingBox(x, y, z, x + 4, y + 7, z + 4);
    }

    @Override
    public boolean addComponentParts(World worldIn, Random random, StructureBoundingBox structureBoundingBoxIn) {
        BlockPos pos = new BlockPos(this.getBoundingBox().minX, this.getBoundingBox().minY, this.getBoundingBox().minZ);

        MinecraftServer server = worldIn.getMinecraftServer();
        TemplateManager templateManager = worldIn.getSaveHandler().getStructureTemplateManager();

        PlacementSettings placementSettings = new PlacementSettings()
                .setRotation(this.rotation)
                .setReplacedBlock(Blocks.STRUCTURE_VOID)
                .setBoundingBox(structureBoundingBoxIn);

        Template template = templateManager.getTemplate(server, WALL);
        template.addBlocksToWorld(worldIn, pos, new CourtyardWallTemplateProcessor(pos, placementSettings), placementSettings.setIntegrity(ComponentNagaCourtyardMain.WALL_INTEGRITY), 2);

        Template temDecay = templateManager.getTemplate(server, WALL_DECAYED);
        temDecay.addBlocksToWorld(worldIn, pos, new MossyCobbleTemplateProcessor(pos, placementSettings), placementSettings.setIntegrity(ComponentNagaCourtyardMain.WALL_DECAY), 2);

        this.setDebugCorners(worldIn);

        return true;
    }
}
