/*
 * (C) Copyright ${year} Nuxeo SA (http://nuxeo.com/) and contributors.
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

package com.optimum.training.operations;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.nuxeo.ecm.automation.core.Constants;
import org.nuxeo.ecm.automation.core.annotations.Context;
import org.nuxeo.ecm.automation.core.annotations.Operation;
import org.nuxeo.ecm.automation.core.annotations.OperationMethod;
import org.nuxeo.ecm.automation.core.annotations.Param;
import org.nuxeo.ecm.core.api.ClientException;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.PathRef;
import org.nuxeo.runtime.api.Framework;

import com.optimum.training.services.OptimumValidLifecycleService;

/**
 * @author Antoine Taillefer (ataillefer@nuxeo.com)
 */
@Operation(id = OptimumLifecycleOperation.ID, category = Constants.CAT_DOCUMENT, label = "OptimumLifecycleOperation", description = "")
public class OptimumLifecycleOperation {

    private static final Log log = LogFactory.getLog(OptimumLifecycleOperation.class);

    public static final String ID = "OptimumLifecycleOperation";

    @Context
    protected CoreSession session;

    @Param(name = "publicationPath")
    protected String publicationPath;

    @OperationMethod
    public DocumentModel run(DocumentModel input) throws ClientException {
        OptimumValidLifecycleService validLifecycleService = Framework.getLocalService(OptimumValidLifecycleService.class);
        if (validLifecycleService.getValidLifeCycleStates().contains(
                input.getCurrentLifeCycleState())) {
            DocumentModel publicationDoc = session.getDocument(new PathRef(
                    publicationPath));
            session.publishDocument(input, publicationDoc);
        } else {
            log.warn("Lifecycle is not valid, can not publish document");
        }
        return null;
    }

}
