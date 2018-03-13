#!/bin/bash
# Copy deployment to standalone JBoss EAP 6.4 and start the server.

cd /home/pwere/Projects/DMDC/applications/jboss-eap-6.4-12-standalone/standalone/deployments
rm -f /home/pwere/Projects/DMDC/applications/jboss-eap-6.4-12-standalone/standalone/deployments/datapwere.war
rm -f /home/pwere/Projects/DMDC/applications/jboss-eap-6.4-12-standalone/standalone/deployments/datapwere.war.*

cd /home/pwere/Projects/DMDC/applications/jboss-eap-6.4-12-standalone/standalone/tmp
rm -rf /home/pwere/Projects/DMDC/applications/jboss-eap-6.4-12-standalone/standalone/tmp/*


cd /home/pwere/Projects/DMDC/applications/jboss-eap-6.4-12-standalone/standalone/log
rm -f /home/pwere/Projects/DMDC/applications/jboss-eap-6.4-12-standalone/standalone/log/*

cd /home/pwere/Projects/DMDC/projects/dbtest/datapwere/
cp /home/pwere/Projects/DMDC/projects/dbtest/datapwere/target/datapwere.war /home/pwere/Projects/DMDC/applications/jboss-eap-6.4-12-standalone/standalone/deployments/

cd /home/pwere/Projects/DMDC/applications/jboss-eap-6.4-12-standalone/bin

./standalone.sh

cd /home/pwere/Projects/DMDC/projects/dbtest/datapwere/

