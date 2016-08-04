node {
    stage 'Checkout'
    git url: 'https://github.com/reasonthearchitect/AD_BidStore.git'

    stage 'Build'
    sh "./gradlew clean build"
    //step([$class: 'JUnitResultArchiver', testResults: '**/build/test-results/TEST-*.xml'])

    stage 'BuildRunDocker'
    sh 'docker kill bidstore'
    sh 'docker rm bidstore'
    sh 'docker build -t reasonthearchitect/bidstore .'
    sh 'docker run -d --name bidstore -p 8215:8215 reasonthearchitect/bidstore'
}