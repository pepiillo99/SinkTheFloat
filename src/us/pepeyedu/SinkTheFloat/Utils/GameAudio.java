package us.pepeyedu.SinkTheFloat.Utils;


import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public abstract class GameAudio extends Thread {
	private String fileName;
	private Clip clip;
	public GameAudio(String fileName) {
		this.fileName = fileName;
		start();
	}
	@Override
	public void run() {
		try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(getClass().getClassLoader().getResource("audio/" + fileName));
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
			onStart();
			sleep(100);
			System.out.println("audio started " + clip.isRunning() + " " + fileName);
			while (clip.isRunning()) {
				sleep(1);
			}
			System.out.println("audio finished " + fileName);
			onFinish();
			currentThread().interrupt();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void onStart() {}
	public abstract void onFinish();
	public float getVolume() {
		FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);        
		return (float) Math.pow(10f, gainControl.getValue() / 20f);
	}

	public void setVolume(float volume) {
		if (volume < 0f || volume > 1f) {
			throw new IllegalArgumentException("Volume not valid: " + volume);
		}
		FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		gainControl.setValue(20f * (float) Math.log10(volume));
	}
}
