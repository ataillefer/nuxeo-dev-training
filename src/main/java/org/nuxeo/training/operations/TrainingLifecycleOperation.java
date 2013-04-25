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

package org.nuxeo.training.operations;

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

/**
 * @author Antoine Taillefer (ataillefer@nuxeo.com)
 */
@Operation(id = TrainingLifecycleOperation.ID, category = Constants.CAT_DOCUMENT, label = "TrainingLifecycleOperation", description = "")
public class TrainingLifecycleOperation {

    private static final Log log = LogFactory.getLog(TrainingLifecycleOperation.class);

    public static final String ID = "TrainingLifecycleOperation";

    @Context
    protected CoreSession session;

    @Param(name = "archivePath")
    protected String archivePath;

    @OperationMethod
    public DocumentModel run(DocumentModel input) throws ClientException {

        // TODO
        return null;
    }

}
