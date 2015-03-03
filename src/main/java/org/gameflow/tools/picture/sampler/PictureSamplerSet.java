package org.gameflow.tools.picture.sampler;


import org.gameflow.utils.MathTools;

import java.util.ArrayList;
import java.util.List;


/**
 * Contains several picture samplers.
 */
public class PictureSamplerSet implements MultiPictureSampler {

    private List<PictureSampler> pictures = new ArrayList<PictureSampler>();

    public PictureSamplerSet() {
    }

    public PictureSamplerSet(List<PictureSampler> pictures) {
        this.pictures = pictures;
    }

    public List<PictureSampler> getPictures() {
        return pictures;
    }

    public void addPicture(PictureSampler pictureSampler) {
        pictures.add(pictureSampler);
    }

    public PictureSampler getPictureSampler(float relativeIndex) {
        if (pictures != null && !pictures.isEmpty()) {
            final int lastPictureIndex = pictures.size() - 1;
            final int index = MathTools.clamp(Math.round(relativeIndex * lastPictureIndex), 0, lastPictureIndex);
            return pictures.get(index);
        }
        else return null;
    }
}
