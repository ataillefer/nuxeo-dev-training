/*
 * (C) Copyright 2006-2012 Nuxeo SA (http://nuxeo.com/) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser General Public License
 * (LGPL) version 2.1 which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-2.1.html
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * Contributors:
 *     Thomas Roger <troger@nuxeo.com>
 */

package com.optimum.training.operations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.nuxeo.ecm.automation.AutomationService;
import org.nuxeo.ecm.automation.OperationChain;
import org.nuxeo.ecm.automation.OperationContext;
import org.nuxeo.ecm.core.api.ClientException;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.test.CoreFeature;
import org.nuxeo.runtime.test.runner.Deploy;
import org.nuxeo.runtime.test.runner.Features;
import org.nuxeo.runtime.test.runner.FeaturesRunner;
import org.nuxeo.runtime.test.runner.LocalDeploy;

import com.google.inject.Inject;

/**
 * @author <a href="mailto:troger@nuxeo.com">Thomas Roger</a>
 * @since 5.6
 */
@RunWith(FeaturesRunner.class)
@Features(CoreFeature.class)
@Deploy({ "org.nuxeo.ecm.automation.core", "org.nuxeo.ecm.automation.features",
        "studio.extensions.ataillefer-SANDBOX", "optimum-training" })
@LocalDeploy("optimum-training:test-valid-lifecyclestates-contrib.xml")
public class TestOperations {

    @Inject
    protected CoreSession session;

    @Inject
    protected AutomationService automationService;

    protected DocumentModel archiveFolder;

    protected DocumentModel contrat1;

    protected DocumentModel contrat2;

    @Before
    public void initTest() throws ClientException {

        archiveFolder = session.createDocument(session.createDocumentModel("/",
                "archives", "Folder"));
        contrat1 = session.createDocument(session.createDocumentModel("/",
                "contrat1", "Contrat"));
        contrat2 = session.createDocument(session.createDocumentModel("/",
                "contrat2", "Contrat"));
    }

    @Test
    public void testOperation() throws Exception {

        contrat2.followTransition("toActif");
        contrat2.followTransition("toClosed");
        session.save();

        OperationContext ctx = new OperationContext(session);
        assertNotNull(ctx);
        OperationChain chain = new OperationChain("testArchiveContracts");

        ctx.setInput(contrat1);
        String archiveFolderPath = archiveFolder.getPathAsString();
        chain.add(OptimumLifecycleOperation.ID).set("archivePath",
                archiveFolderPath);

        DocumentModel result = (DocumentModel) automationService.run(ctx, chain);
        assertNotNull(result);
        assertEquals("/archives/contrat1", result.getPathAsString());

        ctx.setInput(contrat2);
        chain.add(OptimumLifecycleOperation.ID).set("archivePath",
                archiveFolderPath);

        result = (DocumentModel) automationService.run(ctx, chain);
        assertNotNull(result);
        assertEquals("/contrat2", result.getPathAsString());

    }

}
