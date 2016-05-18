package com.example.brewersnotepad.mobile.providers;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.MetadataChangeSet;

/**
 * Created by DragooNArT-PC on 5/18/2016.
 */
public class DriveStorageProvider {

    private GoogleApiClient mGoogleApiClient;
    public DriveStorageProvider(GoogleApiClient googleApiClient) {
        this.mGoogleApiClient = googleApiClient;
    }

    // [START drive_contents_callback]
    final private ResultCallback<DriveApi.DriveContentsResult> driveContentsCallback =
            new ResultCallback<DriveApi.DriveContentsResult>() {
                @Override
                public void onResult(DriveApi.DriveContentsResult result) {
                    if (!result.getStatus().isSuccess()) {
                       // showMessage("Error while trying to create new file contents");
                        return;
                    }

                    MetadataChangeSet changeSet = new MetadataChangeSet.Builder()
                            .setTitle("brewersnotepad_data.bin")
                            .setMimeType("text/plain")
                            .build();
                    Drive.DriveApi.getAppFolder(mGoogleApiClient)
                            .createFile(mGoogleApiClient, changeSet, result.getDriveContents())
                            .setResultCallback(fileCallback);
                }
            };
    // [END drive_contents_callback]

    final private ResultCallback<DriveFolder.DriveFileResult> fileCallback = new
            ResultCallback<DriveFolder.DriveFileResult>() {
                @Override
                public void onResult(DriveFolder.DriveFileResult result) {
                    if (!result.getStatus().isSuccess()) {
                        //showMessage("Error while trying to create the file");
                        return;
                    }
                    result.getDriveFile();
                    //showMessage("Created a file in App Folder: "
                        //    + result.getDriveFile().getDriveId());
                }
            };

}
