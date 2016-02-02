package space.darkowlzz.globalmeditationscope.fragments;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import space.darkowlzz.globalmeditationscope.R;

/**
 * Created by sunny on 30/11/15.
 */
public class IntroSlidePageFragment extends Fragment {

    public static final String ARG_PAGE = "page";

    private int mPageNumber;

    public static IntroSlidePageFragment create(int pageNumber) {
        IntroSlidePageFragment fragment = new IntroSlidePageFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public IntroSlidePageFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNumber = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_intro_slide_page, container, false);
        final ScrollView scrollView = (ScrollView) rootView.findViewById(R.id.content);
        String title = null;
        String desc = null;
        Drawable image = null;

        switch (mPageNumber) {
            case 0:
                title = getString(R.string.page1_title);
                desc = getString(R.string.page1_desc);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    image = getResources().getDrawable(R.drawable.gms_logo_large, getContext().getTheme());
                } else {
                    image = getResources().getDrawable(R.drawable.gms_logo_large);
                }
                break;
            case 1:
                title = getString(R.string.page2_title);
                desc = getString(R.string.page2_desc);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    image = getResources().getDrawable(R.drawable.intro1, getContext().getTheme());
                    scrollView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark, getActivity().getTheme()));
                } else {
                    image = getResources().getDrawable(R.drawable.intro1);
                    scrollView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                }
                break;
            case 2:
                title = getString(R.string.page3_title);
                desc = getString(R.string.page3_desc);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    image = getResources().getDrawable(R.drawable.intro2, getContext().getTheme());
                    scrollView.setBackgroundColor(getResources().getColor(R.color.colorAccent, getActivity().getTheme()));
                } else {
                    image = getResources().getDrawable(R.drawable.intro2);
                    scrollView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                }
                break;
            case 3:
                title = getString(R.string.page4_title);
                desc = getString(R.string.page4_desc);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    image = getResources().getDrawable(R.drawable.intro3, getContext().getTheme());
                    scrollView.setBackgroundColor(getResources().getColor(R.color.green, getActivity().getTheme()));
                } else {
                    image = getResources().getDrawable(R.drawable.intro3);
                    scrollView.setBackgroundColor(getResources().getColor(R.color.green));
                }
                break;
            case 4:
                title = getString(R.string.page5_title);
                desc = getString(R.string.page5_desc);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    image = getResources().getDrawable(R.drawable.intro4, getContext().getTheme());
                    scrollView.setBackgroundColor(getResources().getColor(R.color.colorPrimary, getActivity().getTheme()));
                } else {
                    image = getResources().getDrawable(R.drawable.intro4);
                    scrollView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                }
                break;
            case 5:
                title = getString(R.string.page6_title);
                desc = getString(R.string.page6_desc);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    image = getResources().getDrawable(R.drawable.intro5, getContext().getTheme());
                    scrollView.setBackgroundColor(getResources().getColor(R.color.green, getActivity().getTheme()));
                } else {
                    image = getResources().getDrawable(R.drawable.intro5);
                    scrollView.setBackgroundColor(getResources().getColor(R.color.green));
                }
            default:

        }

        ((TextView) rootView.findViewById(R.id.text1)).setText(title);
        ((TextView) rootView.findViewById(R.id.description)).setText(desc);
        ((ImageView) rootView.findViewById(R.id.intro_image)).setImageDrawable(image);

        return rootView;
    }

    public int getPageNumber() {
        return mPageNumber;
    }
}
