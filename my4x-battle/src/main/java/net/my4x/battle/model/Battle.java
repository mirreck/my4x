package net.my4x.battle.model;

public class Battle {
	private final Step currentStep = Step.START;

	public void processStep() {
		switch (currentStep) {
		case START:
			performStart();
		default:
			finish();
		}
	}

	private void finish() {
		// TODO Auto-generated method stub

	}

	private void performStart() {
		// TODO Auto-generated method stub

	}
}
