package me.shkschneider.dropbearserver;

import android.app.Activity;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import com.astuetz.viewpagertabs.ViewPagerTabProvider;

public class MainAdapter extends PagerAdapter implements ViewPagerTabProvider {

	private static final int SETTINGS_INDEX = 0;
	private static final int SERVER_INDEX = 1;
	private static final int HELP_INDEX = 2;
	private static final int ABOUT_INDEX = 3;

	protected transient Activity mContext;
	private SettingsPage mSettingsPage;
	private ServerPage mServerPage;
	private HelpPage mHelpPage;
	private AboutPage mAboutPage;
	
	private String[] mTitles = {
			"Settings".toUpperCase(),
			"Server".toUpperCase(),
			"Help".toUpperCase(),
			"About".toUpperCase()
	};

	public void initPages() {
		mSettingsPage = new SettingsPage();
		mSettingsPage.initView(mContext);
		mServerPage = new ServerPage();
		mServerPage.initView(mContext);
		mHelpPage = new HelpPage();
		mHelpPage.initView(mContext);
		mAboutPage = new AboutPage();
		mAboutPage.initView(mContext);
	}

	@Override
	public Object instantiateItem(View container, int position) {
		View view = null;
		switch (position) {
		case SETTINGS_INDEX:
			view = mSettingsPage.getView();
			break;
		case SERVER_INDEX:
			view = mServerPage.getView();
			break;
		case HELP_INDEX:
			view = mHelpPage.getView();
			break;
		case ABOUT_INDEX:
			view = mAboutPage.getView();
			break;
		}
		((ViewPager) container).addView(view, 0);
		return view;
	}

	public MainAdapter(Activity context) {
		mContext = context;
	}

	@Override
	public int getCount() {
		return mTitles.length;
	}

	@Override
	public void destroyItem(View container, int position, Object view) {
		((ViewPager) container).removeView((View) view);
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == ((View) object);
	}

	@Override
	public void finishUpdate(View container) {
	}

	@Override
	public void restoreState(Parcelable state, ClassLoader loader) {
	}

	@Override
	public Parcelable saveState() {
		return null;
	}

	@Override
	public void startUpdate(View container) {
	}

	@Override
	public String getTitle(int position) {
		final int len = mTitles.length;
		return (position >= 0 && position < len ? mTitles[position] : "");
	}
}