package linn.examples;

import linn.core.Linn;
import linn.core.execute.LinnExecutor;
import linn.core.execute.state.LinnTurtle;
import linn.core.lang.LinnBuilder;
import processing.core.PApplet;

public class BasicExample extends PApplet {

	private LinnExecutor linnExecutor;

	@Override
	public void setup() {
		final Linn linn = LinnBuilder.newLinn("BasicExample").withAuthor("Thomas Trojer")
				// rule: H ---> ROTATE MOVE-100 H
				.withRule("H").andProduction().yaw(1.0f).F(100).rewrite("H").done()
				// finalize
				.build();
		// configuring the execution environment
		this.linnExecutor = LinnExecutor.newExecutor().useLinn(linn).traceStates(true).onStateChanged(t -> {
			if (t.getPreviousState() == null) {
				// await a second position to draw a line
				return;
			}
			// connect previous and current position with a line
			LinnTurtle tp = t.getPreviousState();
			this.line(400 + (float) tp.getX(), 300 + (float) tp.getY(), 400 + (float) t.getX(), 300 + (float) t.getY());
		})
				// axiom to start the L-System with: H
				.withAxiom().rewrite("H").done();
	}

	@Override
	public void settings() {
		this.size(800, 600);
		this.noLoop();
	}

	@Override
	public void mouseClicked() {
		this.redraw();
	}

	@Override
	public void draw() {
		this.background(255);
		this.linnExecutor.executeOnce();
	}

	public static void main(String _args[]) {
		PApplet.main(new String[] { linn.examples.BasicExample.class.getName() });
	}
}
