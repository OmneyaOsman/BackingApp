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
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.C;
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

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    @BindView(R.id.step_desc_tv)
    TextView stepTextView ;
    @BindView(R.id.step_desc_image)
    ImageView stepIm ;

    private ArrayList<Step> steps ;
    private int currentPosition ;
    private boolean mIsTwoPage ;


    public  static  DescritionOfStepFragment newInstance(int stepPos , ArrayList<Step> steps , boolean mIsTwoPane){
        DescritionOfStepFragment fragment = new DescritionOfStepFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList("steps" , steps);
        args.putInt("pos" , stepPos);
        args.putBoolean("TwoPane" ,mIsTwoPane);

        fragment.setArguments(args);
        return fragment ;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            if (getArguments().containsKey("steps")) {
                currentPosition = getArguments().getInt("pos");
                steps = getArguments().getParcelableArrayList("steps");
                mIsTwoPage = getArguments().getBoolean("TwoPane");

                if (steps != null&& savedInstanceState==null)
                    currentStep = steps.get(currentPosition);
            }
        }
    }

    private long position = C.TIME_UNSET;
    private String desc ="" ,currentVideoUrl ="" ,thumnailUrl="" ;
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
      position =  mExoPlayer.getCurrentPosition();
      outState.putLong("pos" , position);
      outState.putInt("stepPosition" , currentPosition);
      outState.putString("desc" , desc);
      outState.putString("url" , currentVideoUrl);
      outState.putParcelable("currentStep" , currentStep);
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

        if(savedInstanceState!=null) {
            position = savedInstanceState.getLong("pos", C.TIME_UNSET);
            desc = savedInstanceState.getString("desc");
            currentVideoUrl = savedInstanceState.getString("url");
            thumnailUrl = savedInstanceState.getString("image");
            currentStep = savedInstanceState.getParcelable("currentStep");
            currentPosition = savedInstanceState.getInt("stepPosition");
        }else{
            desc =currentStep.getDescription();
            thumnailUrl =currentStep.getThumbnailURL();
            currentVideoUrl = currentStep.getVideoURL();

        }
        stepTextView.setText(desc);
        Glide.with(getActivity())
                .load(thumnailUrl)
                .into(stepIm);

    }


    @Override
    public void onResume() {
        super.onResume();

        hideSystemUi();
        initializePlayer(buildMediaSource(currentVideoUrl));

    }

    @OnClick(R.id.step_next)
    void getNextStep(){
        if(currentPosition>=0 && currentPosition<steps.size()-1){
            currentPosition++;
            currentStep = steps.get(currentPosition);
            updateUI();
        }
    }
    @OnClick(R.id.step_previous)
    void getPreviousStep(){
        if(currentPosition>0 ){
            currentPosition--;
            currentStep = steps.get(currentPosition);
            updateUI();
        }
    }

    private void updateUI(){
        desc = currentStep.getDescription();
        thumnailUrl =currentStep.getThumbnailURL();
        stepTextView.setText(desc);
        Glide.with(getActivity())
                .load(thumnailUrl)
                .into(stepIm);

        currentVideoUrl =currentStep.getVideoURL();


        position = C.TIME_UNSET;
            mExoPlayer.stop();
            mExoPlayer = null ;
            initializePlayer(buildMediaSource(currentVideoUrl));


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
        mExoPlayer.seekTo(position);
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
            position = mExoPlayer.getCurrentPosition();

            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }



}
