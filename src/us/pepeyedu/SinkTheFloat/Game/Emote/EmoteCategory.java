package us.pepeyedu.SinkTheFloat.Game.Emote;

import java.util.Arrays;
import java.util.List;

import us.pepeyedu.SinkTheFloat.Textures.TexturePath;
import us.pepeyedu.SinkTheFloat.Utils.Utils;

public enum EmoteCategory {
	ENFADADO(Arrays.asList(TexturePath.EMOTE_DEMONIO, TexturePath.EMOTE_MOLESTO, TexturePath.EMOTE_MUERTE)),
	INSULTO(Arrays.asList(TexturePath.EMOTE_CACA, TexturePath.EMOTE_FANTASMA, TexturePath.EMOTE_PAYASO)),
	PRESUMIR(Arrays.asList(TexturePath.EMOTE_GAFAS, TexturePath.EMOTE_GUINO)),
	LLORAR(Arrays.asList(TexturePath.EMOTE_LLORANDO, TexturePath.EMOTE_MUERTO, TexturePath.EMOTE_CALLADO)),
	REIR(Arrays.asList(TexturePath.EMOTE_RISA, TexturePath.EMOTE_MUCHARISA)),
	AMABLE(Arrays.asList(TexturePath.EMOTE_BESO, TexturePath.EMOTE_ANGEL, TexturePath.EMOTE_MAJO, TexturePath.EMOTE_VERGONZOSO, TexturePath.EMOTE_FELIZ)),
	OTHER(Arrays.asList(TexturePath.EMOTE_ALIEN, TexturePath.EMOTE_VIRUS, TexturePath.EMOTE_ROBOT, TexturePath.EMOTE_CALABAZA));
	
	private List<TexturePath> emotes;
	EmoteCategory(List<TexturePath> emotes) {
		this.emotes = emotes;
	}
	public List<TexturePath> getEmotes() {
		return emotes;
	}
	public TexturePath getRandom() {
		return emotes.get(Utils.random.nextInt(emotes.size()));
	}
}