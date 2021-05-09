package edu.pse.beast.api.savingloading;

import org.json.JSONObject;
import org.junit.Test;

import edu.pse.beast.api.codegen.loopbounds.LoopBoundHandler;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundType;
import edu.pse.beast.api.savingloading.testruns.LoopBoundHandlerSaverLoaderHelper;

public class LoopBoundHandlerSaverLoaderTest {

	@Test
	public void testLoopBoundSaverLoader() {
		LoopBoundHandler loopBoundHandler = new LoopBoundHandler();

		for (int i = 0; i < 20; ++i) {
			loopBoundHandler.pushLoopBound("main", i,
					LoopBoundType.LOOP_BOUND_AMT_CANDS);
		}

		for (int i = 0; i < 20; ++i) {
			loopBoundHandler.pushLoopBound("voting", i,
					LoopBoundType.LOOP_BOUND_AMT_VOTERS);
		}

		JSONObject json = LoopBoundHandlerSaverLoaderHelper
				.loopboundHandlerToJson(loopBoundHandler);

		LoopBoundHandler loadedLoopBoundHandler = LoopBoundHandlerSaverLoaderHelper
				.loopBoundHandlerFromJSON(json);
		
	}
}
