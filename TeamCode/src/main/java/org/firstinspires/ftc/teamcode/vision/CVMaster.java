package org.firstinspires.ftc.teamcode.vision;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.states.Alliance;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;

public class CVMaster {
    private Alliance alliance = Alliance.BLUE;
    private OpenCvWebcam webcam;
    public PropDetectionPipeline pipeline;
    private HardwareMap hardwareMap;

    boolean isRight;


    public CVMaster(HardwareMap hardwareMap, Alliance alliance, boolean isRight) {
        this.hardwareMap = hardwareMap;
        this.webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "webcam"));
        this.alliance = alliance;
        this.isRight = isRight;
    }

    public void detectProp() {
        pipeline = new PropDetectionPipeline(640, 360, alliance, isRight);
        webcam.setPipeline(pipeline);
        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                webcam.startStreaming(640, 360, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {
            }
        });
    }

    public void stopCamera() {
        webcam.stopStreaming();
    }

    public boolean webcamIsCalibrated() {
        return !webcam.equals(null);
    }

    public void setAlliance(Alliance alliance) {
        this.alliance = alliance;
    }
}

