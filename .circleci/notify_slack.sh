#!/bin/bash

set -ex
set -o pipefail

declare_env_variables() {

  # Declaring environment variables
  #
  # Some environment variables assigned externally are:
  # CIRCLE_TOKEN : This is the API token that is provided for the CircleCI user. Used for accessing artifacts
  # SLACK_CHANNEL_HOOK : This is the webhook for the Slack App where notifications will be sent from
  # DEPLOYMENT_CHANNEL : This is the channel on which the Slack notifications will be posted
  # QEMU_AUDIO_DRV : This will set the Android emulator used for integration tests to have no audio


  # Retrieving the urls for the CircleCI artifacts

  CIRCLE_ARTIFACTS_URL="$(curl https://circleci.com/api/v1.1/project/github/${CIRCLE_PROJECT_USERNAME}/${CIRCLE_PROJECT_REPONAME}/${CIRCLE_BUILD_NUM}/artifacts?circle-token=${CIRCLE_TOKEN} | grep -o 'https://[^"]*')"

  # Assigning slack messages based on the CircleCI job name

  if [ "$CIRCLE_JOB" == 'android_lint' ]; then
    MESSAGE_TEXT="Android Lint Phase Passed! :smirk_cat:"

    # Sorting through the artifact urls to get only the android lint reports

    CIRCLE_REPORT_ARTIFACTS="$(echo $CIRCLE_ARTIFACTS_URL | sed -E -e 's/[[:blank:]]+/\
/g' |  grep '\.html')"
    CIRCLE_ARTIFACTS_BUTTON="$(echo {\"type\": \"button\", \"text\": \"Android Lint Report\", \"url\": \"${CIRCLE_REPORT_ARTIFACTS}\"})"

  elif [ "$CIRCLE_JOB" == 'findbugs_lint' ]; then
    MESSAGE_TEXT="Findbugs Lint Phase Passed! :smirk_cat:"

    # Sorting through the artifact urls to get only the findbugs lint reports

    CIRCLE_REPORT_ARTIFACTS="$(echo $CIRCLE_ARTIFACTS_URL | sed -E -e 's/[[:blank:]]+/\
/g' |  grep 'findbugs\.html')"
    CIRCLE_ARTIFACTS_BUTTON="$(echo {\"type\": \"button\", \"text\": \"Findbugs Lint Report\", \"url\": \"${CIRCLE_REPORT_ARTIFACTS}\"})"

  elif [ "$CIRCLE_JOB" == 'pmd_lint' ]; then
    MESSAGE_TEXT="PMD Lint Phase Passed! :smirk_cat:"

    # Sorting through the artifact urls to get only the PMD lint reports

    CIRCLE_REPORT_ARTIFACTS="$(echo $CIRCLE_ARTIFACTS_URL | sed -E -e 's/[[:blank:]]+/\
/g' |  grep '\.html')"
    CIRCLE_ARTIFACTS_BUTTON="$(echo {\"type\": \"button\", \"text\": \"PMD Lint Report\", \"url\": \"${CIRCLE_REPORT_ARTIFACTS}\"})"

  elif [ "$CIRCLE_JOB" == 'checkstyle_lint' ]; then
    MESSAGE_TEXT="Checkstyle Lint Phase Passed! :smirk_cat:"

    # Sorting through the artifact urls to get only the findbugs lint reports

    CIRCLE_REPORT_ARTIFACTS="$(echo $CIRCLE_ARTIFACTS_URL | sed -E -e 's/[[:blank:]]+/\
/g' |  grep '\.html')"
    CIRCLE_ARTIFACTS_BUTTON="$(echo {\"type\": \"button\", \"text\": \"Checkstyle Lint Report\", \"url\": \"${CIRCLE_REPORT_ARTIFACTS}\"})"
  
  elif [ "$CIRCLE_JOB" == 'report_coverage' ]; then
    MESSAGE_TEXT="Coverage Reporting Passed! :smirk_cat:"

    # Sorting through the artifact urls to get only the unit test and integration test reports
    DEBUG_REPORT="$(echo $CIRCLE_ARTIFACTS_URL | sed -E -e 's/[[:blank:]]+/\
/g' |  grep 'jacocoTestReport\/html\/index\.html')"
    CIRCLE_ARTIFACTS_BUTTON="$(echo \
        "{\"type\": \"button\", \"text\": \"Test Coverage Report\", \"url\": \"${DEBUG_REPORT}\"}", \
    )"

  elif [ "$CIRCLE_JOB" == 'test_unit' ]; then
    MESSAGE_TEXT="Test Phase Passed! :smiley:"

    # Sorting through the artifact urls to get only the unit test and integration test reports
    DEBUG_REPORT="$(echo $CIRCLE_ARTIFACTS_URL | sed -E -e 's/[[:blank:]]+/\
/g' |  grep 'testMockDebugUnitTest\/index\.html')"
    RELEASE_REPORT="$(echo $CIRCLE_ARTIFACTS_URL | sed -E -e 's/[[:blank:]]+/\
/g' |  grep 'testMockReleaseUnitTest\/index\.html')"
    CIRCLE_ARTIFACTS_BUTTON="$(echo \
        "{\"type\": \"button\", \"text\": \"Unit Test Report (Debug)\", \"url\": \"${DEBUG_REPORT}\"}", \
        "{\"type\": \"button\", \"text\": \"Unit Test Report (Release)\", \"url\": \"${RELEASE_REPORT}\"}", \
    )"

  elif [ "$CIRCLE_JOB" == 'deploy_test_build' ]; then
    # Sorting through the artifact urls to get only the apk files
    CIRCLE_APK_ARTIFACTS="$(echo $CIRCLE_ARTIFACTS_URL | sed -E -e 's/[[:blank:]]+/\
/g' |  grep '\.apk')"

    CIRCLE_DEBUG_ARTIFACT="$(echo $CIRCLE_APK_ARTIFACTS | sed -E -e 's/[[:blank:]]+/\
/g' |  grep 'debug[a-z0-9.-]*[a-z0-9.-]*.apk$')"
    CIRCLE_ARTIFACTS_BUTTON="$(echo \
        "{\"type\": \"button\", \"text\": \"Debug APK\", \"url\": \"${CIRCLE_DEBUG_ARTIFACT}\"}", \
    )"

    MESSAGE_TEXT="Deploy Test Build Succeeded :rocket:"


  elif [ "$CIRCLE_JOB" == 'test_instrumentation' ]; then
    MESSAGE_TEXT="Instrumentation Test Passed! :smirk_cat:"

  elif [ "$CIRCLE_JOB" == 'deploy_staging_build' ]; then
    CIRCLE_APK_ARTIFACTS="$(echo $CIRCLE_ARTIFACTS_URL | sed -E -e 's/[[:blank:]]+/\
/g' |  grep '\.apk')"

    CIRCLE_DEBUG_ARTIFACT="$(echo $CIRCLE_APK_ARTIFACTS | sed -E -e 's/[[:blank:]]+/\
/g' |  grep 'debug[a-z0-9.-]*[a-z0-9.-]*.apk$')"
    CIRCLE_RELEASE_ARTIFACT="$(echo $CIRCLE_APK_ARTIFACTS | sed -E -e 's/[[:blank:]]+/\
/g' |  grep 'release[a-z0-9.-]*[a-z0-9.-]*.apk$')"
    CIRCLE_ARTIFACTS_BUTTON="$(echo \
        "{\"type\": \"button\", \"text\": \"Debug APK\", \"url\": \"${CIRCLE_DEBUG_ARTIFACT}\"}", \
        "{\"type\": \"button\", \"text\": \"Release APK\", \"url\": \"${CIRCLE_RELEASE_ARTIFACT}\"}" \
    )"

    MESSAGE_TEXT="Deploy Staging Build Succeeded :rocket:"

  elif [ "$CIRCLE_JOB" == 'deploy_debug_build' ]; then
    CIRCLE_APK_ARTIFACTS="$(echo $CIRCLE_ARTIFACTS_URL | sed -E -e 's/[[:blank:]]+/\
    /g' |  grep '\.apk')"

    CIRCLE_MOCK_DEBUG_ARTIFACT="$(echo -e $CIRCLE_APK_ARTIFACTS | sed -E -e 's/[[:blank:]]+/\
/g' |  grep 'mock-debug[a-z0-9.-]*[a-z0-9.-]*.apk$' | tr -d '[:blank:]')"

    CIRCLE_PROD_DEBUG_ARTIFACT="$(echo -e $CIRCLE_APK_ARTIFACTS | sed -E -e 's/[[:blank:]]+/\
/g' |  grep 'prod-debug[a-z0-9.-]*[a-z0-9.-]*.apk$' | tr -d '[:blank:]')"

    CIRCLE_ARTIFACTS_BUTTON="$(echo \
        "{\"type\": \"button\", \"text\": \"Mock debug APK\", \"url\": \"${CIRCLE_MOCK_DEBUG_ARTIFACT}\"}", \
        "{\"type\": \"button\", \"text\": \"Prod debug APK\", \"url\": \"${CIRCLE_PROD_DEBUG_ARTIFACT}\"}" \
    )"

    MESSAGE_TEXT="Deploy Debug Build Succeeded :rocket:"

    echo "============================="
    echo $CIRCLE_APK_ARTIFACTS
    echo "============================="
    echo $CIRCLE_MOCK_DEBUG_ARTIFACT
    echo "============================="
    echo $CIRCLE_PROD_DEBUG_ARTIFACT
    echo "============================="
    echo $CIRCLE_ARTIFACTS_BUTTON
    echo "============================="

  elif [ "$CIRCLE_JOB" == 'deploy_production_build' ]; then
    CIRCLE_APK_ARTIFACTS="$(echo $CIRCLE_ARTIFACTS_URL | sed -E -e 's/[[:blank:]]+/\
/g' |  grep '\.apk')"

    CIRCLE_RELEASE_ARTIFACT="$(echo $CIRCLE_APK_ARTIFACTS | sed -E -e 's/[[:blank:]]+/\
/g' |  grep 'release[a-z0-9.-]*[a-z0-9.-]*.apk$')"
    CIRCLE_ARTIFACTS_BUTTON="$(echo \
        "{\"type\": \"button\", \"text\": \"Release APK\", \"url\": \"${CIRCLE_RELEASE_ARTIFACT}\"}" \
    )"

    MESSAGE_TEXT="Deploy Production Build Succeeded :rocket:"


  else
    MESSAGE_TEXT="Unknown Task"
    CIRCLE_ARTIFACTS_MESSAGE="No artifacts for unknown job"
  fi

  # Some template for the Slack message

  COMMIT_LINK="https://github.com/${CIRCLE_PROJECT_USERNAME}/${CIRCLE_PROJECT_REPONAME}/commit/${CIRCLE_SHA1}"
  IMG_TAG="$(git rev-parse --short HEAD)"
  CIRCLE_WORKFLOW_URL="https://circleci.com/workflow-run/${CIRCLE_WORKFLOW_ID}"
  SLACK_TEXT_TITLE="CircleCI Build #$CIRCLE_BUILD_NUM"
  SLACK_DEPLOYMENT_TEXT="Executed Git Commit <$COMMIT_LINK|${IMG_TAG}>: ${MESSAGE_TEXT}"
}

send_notification() {

  # Sending the Slack notification

  curl -X POST --data-urlencode \
  "payload={
      \"channel\": \"${DEPLOYMENT_CHANNEL}\",
      \"username\": \"DeployNotification\",
      \"attachments\": [{
          \"fallback\": \"CircleCI build notification and generated files\",
          \"color\": \"good\",
          \"author_name\": \"Branch: $CIRCLE_BRANCH by ${CIRCLE_USERNAME}\",
          \"author_link\": \"https://github.com/AndelaOSP/art-android/tree/${CIRCLE_BRANCH}\",
          \"title\": \"${SLACK_TEXT_TITLE}\",
          \"title_link\": \"$CIRCLE_WORKFLOW_URL\",
          \"text\": \"${SLACK_DEPLOYMENT_TEXT}\",
          \"actions\": [${CIRCLE_ARTIFACTS_BUTTON}]
      }]
  }" \
  "${SLACK_CHANNEL_HOOK}"
}

main() {
  echo "Declaring environment variables"
  declare_env_variables

  echo "Sending notification"
  send_notification

}

main "$@"
