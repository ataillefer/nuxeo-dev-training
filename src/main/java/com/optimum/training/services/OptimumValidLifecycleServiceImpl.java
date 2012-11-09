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

package com.optimum.training.services;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.nuxeo.ecm.core.api.ClientException;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModelList;
import org.nuxeo.runtime.model.ComponentInstance;
import org.nuxeo.runtime.model.DefaultComponent;

/**
 * @author Antoine Taillefer (ataillefer@nuxeo.com)
 */
public class OptimumValidLifecycleServiceImpl extends DefaultComponent
        implements OptimumValidLifecycleService {

    private static final long serialVersionUID = 1170905776749379666L;

    private static final Log log = LogFactory.getLog(OptimumValidLifecycleServiceImpl.class);

    protected static final String VALID_LIFECYCLE_STATES_EXTENSION_POINT = "validLifecycleStates";

    @Override
    public void registerContribution(Object contribution,
            String extensionPoint, ComponentInstance contributor)
            throws Exception {

        if (VALID_LIFECYCLE_STATES_EXTENSION_POINT.equals(extensionPoint)) {
            if (contribution instanceof OptimumValidLifecycleDescriptor) {
                registerValidLifecycleStates((OptimumValidLifecycleDescriptor) contribution);
            }
        }
        super.registerContribution(contribution, extensionPoint, contributor);
    }

    protected final void registerValidLifecycleStates(
            OptimumValidLifecycleDescriptor contribution) {

        // TODO

    }

    @Override
    public List<String> getValidLifeCycleStates() {
        // TODO
        return null;
    }

    @Override
    public DocumentModelList getValidDocuments(String type, CoreSession session)
            throws ClientException {

        // TODO
        return null;

    }

}
