package edu.pse.beast.propertylist.View;

import edu.pse.beast.highlevel.ResultInterface;
import edu.pse.beast.highlevel.ResultPresenterElement;


	public class InterfaceTest implements ResultInterface {
		public InterfaceTest() {
			
		}

		@Override
		public boolean readyToPresent() {
			return true;
		}

		@Override
		public void presentTo(ResultPresenterElement presenter) {
			presenter.presentSuccess();
			
		}
		
	}

