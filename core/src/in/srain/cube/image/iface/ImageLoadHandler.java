package in.srain.cube.image.iface;

import android.graphics.drawable.BitmapDrawable;

import in.srain.cube.image.CubeImageView;
import in.srain.cube.image.ImageTask;

/**
 * A handler that used in loading a image.
 * 
 * <p>
 * {@link ImageLoadHandler#onLoading(ImageTask)} will be called when begin to load the image.
 * </p>
 * 
 * <p>
 * {@link ImageLoadHandler#onLoadFinish(ImageTask, BitmapDrawable)} will be called after the image is loaded.
 * </p>
 */
public interface ImageLoadHandler {

	/**
	 * When begin to load the image from disk or network.
	 */
	void onLoading(ImageTask imageTask, CubeImageView cubeImageView);

	/**
	 * After image is loaded.
	 */
	void onLoadFinish(ImageTask imageTask, CubeImageView cubeImageView, BitmapDrawable drawable);
}