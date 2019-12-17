// Lien vers Nexus, doit correspondre à l'instance paramétrée dans Jenkins
def nexusId = 'nexus_localhost'

/* *** Configuration de Nexus pour Maven ***/
// URL de Nexus
def nexusUrl = 'http://localhost:8081'
// Repo Id (provient du settings.xml nexus pour récupérer user/password)
def mavenRepoId = 'maintenance'

/* *** Repositories Nexus *** */
def nexusRepoSnapshot = "maven-snapshots"
def nexusRepoRelease = "maven-releases"



/* *** Détail du projet, récupéré dans le pipeline en lisant le pom.xml *** */
def groupId = ''
def artefactId = ''
def filePath = ''
def packaging = ''
def version = ''

// Variable utilisée pour savoir si c'est une RELEASE ou une SNAPSHOT
def isSnapshot = true

pipeline {
   agent any

   stages {
      stage('Build') {
          steps {
              sh 'mvn clean package'
          }
      }
	  
	  stage('Analyse statique') {
          steps {
              sh 'mvn checkstyle:checkstyle'
			  sh 'mvn spotbugs:spotbugs'
          }
      }

   }

   post {
      always{
        junit '**/surefire-reports/*.xml'
        recordIssues enabledForFailure : true, tools:checkStyle()
        recordIssues enabledForFailure : true, tools:spotBugs()
        recordIssues enabledForFailure : true, tools: cpd(pattern:'**/target/cpd.xml')
        recordIssues enabledForFailure : true, tools: pmdParser(pattern:'**/target/pmd.xml')
        recordIssues enabledForFailure : true, tools:[mavenConsole(),java(),javaDoc()]
      }
   }
}