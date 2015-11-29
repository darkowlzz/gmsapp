package space.darkowlzz.globalmeditationscope;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

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

        switch (mPageNumber) {
            case 0:
                title = getString(R.string.page1_title);
                desc = getString(R.string.page1_desc);
                break;
            case 1:
                title = getString(R.string.page2_title);
                desc = getString(R.string.page2_desc);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    scrollView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark, getActivity().getTheme()));
                } else {
                    scrollView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                }
                break;
            case 2:
                title = getString(R.string.page3_title);
                desc = getString(R.string.page3_desc);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    scrollView.setBackgroundColor(getResources().getColor(R.color.green, getActivity().getTheme()));
                } else {
                    scrollView.setBackgroundColor(getResources().getColor(R.color.green));
                }
                break;
            case 3:
                title = getString(R.string.page4_title);
                desc = getString(R.string.page4_desc);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    scrollView.setBackgroundColor(getResources().getColor(R.color.colorAccent, getActivity().getTheme()));
                } else {
                    scrollView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                }
                break;
            case 4:
                title = getString(R.string.page5_title);
                desc = getString(R.string.page5_desc);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    scrollView.setBackgroundColor(getResources().getColor(R.color.colorPrimary, getActivity().getTheme()));
                } else {
                    scrollView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                }
                break;
            case 5:
                title = getString(R.string.page6_title);
                desc = getString(R.string.page6_desc);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    scrollView.setBackgroundColor(getResources().getColor(R.color.green, getActivity().getTheme()));
                } else {
                    scrollView.setBackgroundColor(getResources().getColor(R.color.green));
                }
            default:

        }

        ((TextView) rootView.findViewById(R.id.text1)).setText(title);
        ((TextView) rootView.findViewById(R.id.description)).setText(desc);

        return rootView;
    }

    public int getPageNumber() {
        return mPageNumber;
    }
}
