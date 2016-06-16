package org.spark.model;

import java.io.IOException;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.spark.PropertiesUtils;

import static org.mockito.Mockito.*;

public class PropertiesUtilsTest {

	private PropertiesUtils propertiesUtils;
	
	@Mock
	private Properties properties;
	 
	@Before
	public void init() throws IOException{
		MockitoAnnotations.initMocks(this);
		PropertiesUtils.setProperties(properties);
		propertiesUtils = new PropertiesUtils();
	}
	  
	@Test
	public void deveVerificarSeFoiChamadoPeloMenosUmaVezAChaveCassandraKeysoace() throws IOException{
		when(properties.getProperty(anyString())).thenReturn("mock");

		Assert.assertEquals("mock", this.propertiesUtils.getCassandraKeyspace());
		verify(properties, Mockito.atLeastOnce()).getProperty("cassandra.keyspace");
	}
	
	@Test
	public void deveVerificarSeFoiChamadoPeloMenosUmaVezAChaveCassandraIP() throws IOException{
		when(properties.getProperty(anyString())).thenReturn("mock");

		Assert.assertEquals("mock", this.propertiesUtils.getCassandraIP());
		verify(properties, Mockito.atLeastOnce()).getProperty("cassandra.ip");
	}
	
	@Test
	public void deveVerificarSeFoiChamadoPeloMenosUmaVezAChaveCassandraTableOut() throws IOException{
		when(properties.getProperty(anyString())).thenReturn("mock");

		Assert.assertEquals("mock", this.propertiesUtils.getCassandraTableOut());
		verify(properties, Mockito.atLeastOnce()).getProperty("cassandra.table.out");
	}
	
	@Test
	public void deveVerificarSeFoiChamadoPeloMenosUmaVezAChaveCassandraUser() throws IOException{
		when(properties.getProperty(anyString())).thenReturn("mock");

		Assert.assertEquals("mock", this.propertiesUtils.getCassandraUser());
		verify(properties, Mockito.atLeastOnce()).getProperty("cassandra.user");
	}
	
	
	@Test
	public void deveVerificarSeFoiChamadoPeloMenosUmaVezAChaveCassandraPass() throws IOException{
		when(properties.getProperty(anyString())).thenReturn("mock");

		Assert.assertEquals("mock", this.propertiesUtils.getCassandraPass());
		verify(properties, Mockito.atLeastOnce()).getProperty("cassandra.pass");
	}
	
	
	@Test
	public void deveVerificarSeFoiChamadoPeloMenosUmaVezAChaveCassandraFacebook() throws IOException{
		when(properties.getProperty(anyString())).thenReturn("true");

		Assert.assertEquals(true, this.propertiesUtils.getCassandraFacebook());
		verify(properties, Mockito.atLeastOnce()).getProperty("cassandra.facebook");
	}
	
	@Test
	public void deveVerificarSeFoiChamadoPeloMenosUmaVezAChaveCassandraTwitter() throws IOException{
		when(properties.getProperty(anyString())).thenReturn("false");

		Assert.assertEquals(false, this.propertiesUtils.getCassandraTwitter());
		verify(properties, Mockito.atLeastOnce()).getProperty("cassandra.twitter");
	}
	
	@Test
	public void deveVerificarSeFoiChamadoPeloMenosUmaVezAChaveCassandraMaster() throws IOException{
		when(properties.getProperty(anyString())).thenReturn("mock");

		Assert.assertEquals("mock", this.propertiesUtils.getCassandraMaster());
		verify(properties, Mockito.atLeastOnce()).getProperty("spark.master");
	}
}
