package com.omni.backingapp.fragment;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.omni.backingapp.R;
import com.omni.backingapp.model.Step;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Omni on 24/11/2017.
 */

public class DescritionOfStepFragment extends Fragment {


    private Step currentStep ;

    private SimpleExoPlayer mExoPlayer;
    @BindView(R.id.playerView)
     SimpleExoPlayerView mPlayerView;

    @BindView(R.id.step_next)
    Button nextStep ;

    @BindView(R.id.step_previous)
    Button previousStep ;


    public  static  DescritionOfStepFragment newInstance(Step currentStep){
        DescritionOfStepFragment fragment = new DescritionOfStepFragment();
        Bundle args = new Bundle();
        args.putParcelable("currentStep" , currentStep);
        fragment.setArguments(args);
        return fragment ;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
            if(getArguments().containsKey("currentStep"))
            {
                currentStep = getArguments().getParcelable("currentStep");
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.desc_of_step_fragment , container , false);
        ButterKnife.bind(this , view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        initializePlayer(buildMediaSource(currentStep.getVideoURL()));
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer(buildMediaSource(currentStep.getVideoURL()));        }
    }

    @Override
    public void onResume() {
        super.onResume();
        hideSystemUi();
        if ((Util.SDK_INT <= 23 || mExoPlayer == null)) {
            initializePlayer(buildMediaSource(currentStep.getVideoURL()));
        }
    }



    private void initializePlayer(MediaSource mediaSource) {
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);
            // Prepare the MediaSource.
//            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(getActivity(), userAgent)
//                    , new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);

        }
    }


    private MediaSource buildMediaSource(String mediaUrL) {

        Uri mediaUri = Uri.parse(mediaUrL);
        String userAgent = Util.getUserAgent(getActivity(), "StepVideo");

        return new ExtractorMediaSource(mediaUri,
                new DefaultHttpDataSourceFactory(userAgent),
                new DefaultExtractorsFactory(), null, null);
    }


    @SuppressLint("InlinedApi")
    private void hideSystemUi() {
        mPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }


    /**
     * Release ExoPlayer.
     */
    private void releasePlayer() {
        if (mExoPlayer != null) {
//            playbackPosition = mExoPlayer.getCurrentPosition();
//            currentWindow = mExoPlayer.getCurrentWindowIndex();
//            playWhenReady = mExoPlayer.getPlayWhenReady();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }


}