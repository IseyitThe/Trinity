package me.leon.trinity.hacks.movement;

import me.leon.trinity.events.main.EventPacketRecieve;
import me.leon.trinity.hacks.Category;
import me.leon.trinity.hacks.Module;
import me.leon.trinity.setting.rewrite.BooleanSetting;
import me.leon.trinity.setting.rewrite.SliderSetting;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.network.play.server.SPacketExplosion;

public class Velocity extends Module {
	public static SliderSetting x = new SliderSetting("X modifier", 0, 0, 100, true);
	public static SliderSetting y = new SliderSetting("Y modifier", 0, 0, 100, true);
	public static SliderSetting z = new SliderSetting("Z modifier", 0, 0, 100, true);
	public static BooleanSetting reverse = new BooleanSetting("Reverse", false);

	@EventHandler
	private final Listener<EventPacketRecieve> packetRecieveListener = new Listener<>(event -> {
		if (nullCheck()) return;

		if (event.getPacket() instanceof SPacketExplosion) {
			if (x.getValue() == 0 && z.getValue() == 0 && y.getValue() == 0) {
				event.cancel();
				return;
			}
			SPacketExplosion packet = (SPacketExplosion) event.getPacket();
			packet.motionX = (float) (reverse.getValue() ? (-x.getValue() / 100f) * packet.motionX : (x.getValue() / 100f) * packet.motionX);
			packet.motionY = (float) (reverse.getValue() ? (-y.getValue() / 100f) * packet.motionY : (y.getValue() / 100f) * packet.motionY);
			packet.motionZ = (float) (reverse.getValue() ? (-z.getValue() / 100f) * packet.motionZ : (z.getValue() / 100f) * packet.motionZ);
			mc.player.updateEntityActionState();
		}

		if (event.getPacket() instanceof SPacketEntityVelocity) {
			if (x.getValue() == 0 && z.getValue() == 0 && y.getValue() == 0) {
				event.cancel();
				return;
			}
			SPacketEntityVelocity packet = (SPacketEntityVelocity) event.getPacket();
			if (packet.entityID == mc.player.entityId) {
				packet.motionX = (int) (reverse.getValue() ? (-x.getValue() / 100) * packet.motionX : (x.getValue() / 100) * packet.motionX);
				packet.motionY = (int) (reverse.getValue() ? (-y.getValue() / 100) * packet.motionY : (y.getValue() / 100) * packet.motionY);
				packet.motionZ = (int) (reverse.getValue() ? (-z.getValue() / 100) * packet.motionZ : (z.getValue() / 100) * packet.motionZ);
			}
		}
	});

	public Velocity() {
		super("Velocity", "Anti-KB", Category.MOVEMENT);
	}
}
