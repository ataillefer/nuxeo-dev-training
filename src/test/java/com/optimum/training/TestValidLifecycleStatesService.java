/*
 * (C) Copyright 2012 Nuxeo SA (http://nuxeo.com/) and contributors.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser General Public License
 * (LGPL) version 2.1 which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl.html
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * Contributors:
 *     Antoine Taillefer
 */
package com.optimum.training;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.nuxeo.ecm.core.api.ClientException;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.DocumentModelList;
import org.nuxeo.ecm.core.event.EventServiceAdmin;
import org.nuxeo.ecm.core.test.CoreFeature;
import org.nuxeo.runtime.test.runner.Deploy;
import org.nuxeo.runtime.test.runner.Features;
import org.nuxeo.runtime.test.runner.FeaturesRunner;
import org.nuxeo.runtime.test.runner.LocalDeploy;

import com.google.inject.Inject;
import com.optimum.training.services.OptimumValidLifecycleService;

/**
 * Tests the Contrat document type.
 *
 * @author Antoine Taillefer (ataillefer@nuxeo.com)
 */
@RunWith(FeaturesRunner.class)
@Features(CoreFeature.class)
@Deploy({ "studio.extensions.ataillefer-SANDBOX", "optimum-training" })
@LocalDeploy("optimum-training:test-valid-lifecyclestates-contrib.xml")
public class TestValidLifecycleStatesService {

    @Inject
    protected CoreSession session;

    @Inject
    protected OptimumValidLifecycleService ovls;

    @Inject
    protected EventServiceAdmin eventAdminService;

    @Before
    public void init() {
        eventAdminService.setBlockAsyncHandlers(true);
    }

    @Test
    public void testValidLifeCycleStatesService() throws ClientException {

        List<String> validLifeCycleStates = ovls.getValidLifeCycleStates();
        assertNotNull(validLifeCycleStates);
        assertEquals(2, validLifeCycleStates.size());
        assertEquals("new", validLifeCycleStates.get(0));
        assertEquals("active", validLifeCycleStates.get(1));

        DocumentModel contrat1 = session.createDocument(session.createDocumentModel(
                "/", "contrat1", "Contrat"));
        DocumentModel contrat2 = session.createDocument(session.createDocumentModel(
                "/", "contrat2", "Contrat"));
        DocumentModel contrat3 = session.createDocument(session.createDocumentModel(
                "/", "contrat3", "Contrat"));
        session.save();

        contrat2.followTransition("toActive");
        contrat3.followTransition("toActive");
        contrat3.followTransition("toClosed");
        session.save();

        DocumentModelList dml = ovls.getValidDocuments("Contrat", session);
        assertNotNull(dml);
        assertEquals(2, dml.size());
    }
}
