package net.lqhe.farm;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.Queue;

public class AutoFarm implements ModInitializer {
	private static Queue<Runnable> taskQueue = new LinkedList<>();
	@Override
	public void onInitialize() {
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			try {
				doTask();
			} catch (InvocationTargetException | IllegalAccessException ignored) {
			}
		});
	}
	private void doTask() throws InvocationTargetException, IllegalAccessException {
		Runnable task = taskQueue.poll();
		if (task!= null) task.run();
	}

	public static void registerTask(Runnable method){
		taskQueue.offer(method);
	}
}
