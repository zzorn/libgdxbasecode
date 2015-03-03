package org.gameflow.tools.picture.sampler;

import com.badlogic.gdx.math.MathUtils;
import org.gameflow.tools.picture.Picture;

import java.util.ArrayList;
import java.util.List;


/**
 * Contains several pictures.
 */
public class PictureSamplerSet implements MultiPictureSampler {

    private List<PictureSampler> pictures = new ArrayList<Picture>();

    public PictureSamplerSet() {
    }

    public PictureSamplerSet(List<Picture> pictures) {
        this.pictures = pictures;
    }

    public List<Picture> getPictures() {
        return pictures;
    }

    public void addPicture

    public PictureSampler getPictureSampler(float relativeIndex) {
        if (pictures != null && !pictures.isEmpty()) {
            final int lastPictureIndex = pictures.size() - 1;
            final int index = clamp(Math.round(relativeIndex * lastPictureIndex), 0, lastPictureIndex);
            return pictures.get(index);
        }
        else return null;
    }
}
