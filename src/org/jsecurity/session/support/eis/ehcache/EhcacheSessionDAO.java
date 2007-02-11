/*
 * Copyright (C) 2005-2007 Les Hazlewood
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation; either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General
 * Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the
 *
 * Free Software Foundation, Inc.
 * 59 Temple Place, Suite 330
 * Boston, MA 02111-1307
 * USA
 *
 * Or, you may view it online at
 * http://www.opensource.org/licenses/lgpl-license.php
 */
package org.jsecurity.session.support.eis.ehcache;

import net.sf.ehcache.CacheManager;
import org.jsecurity.cache.ehcache.EhCacheProvider;
import org.jsecurity.session.support.eis.support.MemorySessionDAO;

/**
 * Provides memory caching <em>and</em> disk-based caching for production environments via Ehcache.
 *
 * @since 0.2
 * @author Les Hazlewood
 */
public class EhcacheSessionDAO extends MemorySessionDAO {

    private CacheManager manager;
    private String configurationResourceName = "/org/jsecurity/session/support/eis/ehcache/EhcacheSessionDAO.defaultSettings.ehcache.xml";

    public EhcacheSessionDAO() {
        setCacheProvider( new EhCacheProvider() );
        setMaintainStoppedSessions( false );
    }

    public void setCacheManager( CacheManager cacheManager ) {
        this.manager = cacheManager;
    }

    public void setConfigurationResourceName( String configurationResourceName ) {
        this.configurationResourceName = configurationResourceName;
    }

    public void init() {
        EhCacheProvider provider = (EhCacheProvider)this.cacheProvider;
        provider.setConfigurationResourceName( configurationResourceName );

        if ( manager != null ) {
            provider.setCacheManager( manager );
        }

        provider.init();

        if ( manager == null ) {
            setCacheManager( provider.getCacheManager() );
        }

        super.init();
    }
}
