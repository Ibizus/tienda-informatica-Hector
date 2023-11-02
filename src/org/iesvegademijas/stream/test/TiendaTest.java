package org.iesvegademijas.stream.test;

import static org.junit.jupiter.api.Assertions.*;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;
import static java.util.Comparator.*;

import org.iesvegademijas.hibernate.Fabricante;
import org.iesvegademijas.hibernate.FabricanteHome;
import org.iesvegademijas.hibernate.Producto;
import org.iesvegademijas.hibernate.ProductoHome;
import org.junit.jupiter.api.Test;


class TiendaTest {

	// PRIMERO crea contenedor: sudo docker run  -p 30306:3306 --name mysqldb -e MYSQL_ROOT_PASSWORD=user mysql:latest
	// ANTES DE LANZAR LOS TEST: sudo docker start mysqldb
	
	@Test
	void testSkeletonFabricante() {
	
		FabricanteHome fabHome = new FabricanteHome();
		
		try {
			fabHome.beginTransaction();
	
			List<Fabricante> listFab = fabHome.findAll();

			//TODO STREAMS
		
			fabHome.commitTransaction();
		}
		catch (RuntimeException e) {
			fabHome.rollbackTransaction();
		    throw e; // or display error message
		}
	}
	

	@Test
	void testSkeletonProducto() {
	
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();		
						
			//TODO STREAMS

			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	@Test
	void testAllFabricante() {
	
		FabricanteHome fabHome = new FabricanteHome();
		
		try {
			fabHome.beginTransaction();
	
			List<Fabricante> listFab = fabHome.findAll();
			assertEquals(9,listFab.size());
		
			fabHome.commitTransaction();
		}
		catch (RuntimeException e) {
			fabHome.rollbackTransaction();
		    throw e; // or display error message
		}
	}
	
	@Test
	void testAllProducto() {
	
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();		
			assertEquals(11,listProd.size());
		
			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
	}
	
	/**
	 * 1. Lista los nombres y los precios de todos los productos de la tabla producto
	 */
	@Test
	void test1() {
	
		ProductoHome prodHome = new ProductoHome();
		
		try {
			prodHome.beginTransaction();
			
			List<Producto> listProd = prodHome.findAll();

			List<String> listaNombrePrecio = listProd.stream().map(
					p -> "Nombre: " + p.getNombre() + ", Precio: " + p.getPrecio()).toList();

			Set<String[]> setNombrePrecio = listProd.stream()
					.map(producto -> new String[]{"Nombre: " + producto.getNombre(), ", Precio: " + Double.toString(producto.getPrecio())})
					.collect(toSet());

			listaNombrePrecio.forEach(System.out::println);

			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
	}

	/**
	 * 2. Devuelve una lista de Producto completa con el precio de euros convertido a dólares.
	 */
	@Test
	void test2() {
		
		ProductoHome prodHome = new ProductoHome();
		
		try {
			prodHome.beginTransaction();			
			List<Producto> listProd = prodHome.findAll();

			DecimalFormat df = new DecimalFormat("###.##");

			List<String> listaPrecioDolares = listProd.stream().map(
					p->  "Name: " + p.getNombre() + ", Price: " + df.format(p.getPrecio()*1.06) + "$").toList();

			listaPrecioDolares.forEach(p -> System.out.println(p));

			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 3. Lista los nombres y los precios de todos los productos, convirtiendo los nombres a mayúscula.
	 */
	@Test
	void test3() {

		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();		
						
			//TODO STREAMS
			List<String> listaNombreMayu = listProd.stream().map(
					p -> "Nombre: " + p.getNombre().toUpperCase() + ", Precio: " + p.getPrecio()).toList();

			listaNombreMayu.forEach(s -> System.out.println(s));

			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 4. Lista el nombre de todos los fabricantes y a continuación en mayúsculas los dos primeros caracteres del nombre del fabricante.
	 */
	@Test
	void test4() {
	
		FabricanteHome fabHome = new FabricanteHome();
		
		try {
			fabHome.beginTransaction();
	
			List<Fabricante> listFab = fabHome.findAll();
					
			//TODO STREAMS
			List<String> listaFabricantes = listFab.stream().map(
					f -> "Fabricante: " + f.getNombre() + ", Cód: " + f.getNombre().substring(0,2).toUpperCase()).toList();

			listaFabricantes.forEach(f -> System.out.println(f));
					
			fabHome.commitTransaction();
		}
		catch (RuntimeException e) {
			fabHome.rollbackTransaction();
		    throw e; // or display error message
		}
	}
	
	/**
	 * 5. Lista el código de los fabricantes que tienen productos.
	 */
	@Test
	void test5() {
	
		FabricanteHome fabHome = new FabricanteHome();
		
		try {
			fabHome.beginTransaction();
	
			List<Fabricante> listFab = fabHome.findAll();
					
			//TODO STREAMS
			List<String> listaFabConProductos = listFab.stream().filter(
					f -> !f.getProductos().isEmpty()).map(
							f -> "Cód Fabricante: " + f.getCodigo() + " (" + f.getNombre() + ")").toList();

			listaFabConProductos.forEach(f -> System.out.println(f));
			fabHome.commitTransaction();
		}
		catch (RuntimeException e) {
			fabHome.rollbackTransaction();
		    throw e; // or display error message
		}
	}
	
	/**
	 * 6. Lista los nombres de los fabricantes ordenados de forma descendente.
	 */
	@Test
	void test6() {
	
		FabricanteHome fabHome = new FabricanteHome();
		
		try {
			fabHome.beginTransaction();
	
			List<Fabricante> listFab = fabHome.findAll();
					
			//TODO STREAMS
			List<String> listaFabricantesOrder = listFab.stream().sorted(comparing(Fabricante::getNombre).reversed()).map(
					f -> f.getNombre()).toList();

			listaFabricantesOrder.forEach(f -> System.out.println(f));
		
			fabHome.commitTransaction();
		}
		catch (RuntimeException e) {
			fabHome.rollbackTransaction();
		    throw e; // or display error message
		}
	}
	
	/**
	 * 7. Lista los nombres de los productos ordenados en primer lugar por el nombre de forma ascendente y en segundo lugar por el precio de forma descendente.
	 */
	@Test
	void test7() {
	
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();		
						
			//TODO STREAMS

			List<String> listaProdNombrePrecio = listProd.stream().sorted(comparing(Producto::getNombre).thenComparing(comparing(Producto::getPrecio).reversed())).map(
					p -> p.getNombre() + " => " + p.getPrecio() + "€").toList();

			listaProdNombrePrecio.forEach(p -> System.out.println(p));

			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 8. Devuelve una lista con los 5 primeros fabricantes.
	 */
	@Test
	void test8() {
	
		FabricanteHome fabHome = new FabricanteHome();
		
		try {
			fabHome.beginTransaction();
	
			List<Fabricante> listFab = fabHome.findAll();
					
			//TODO STREAMS
			List<String> lista5fab = listFab.stream().sorted(comparing(Fabricante::getNombre)).limit(5).map(
					f -> "Name: " + f.getNombre() + ", Code: " + f.getCodigo()).toList();

			lista5fab.forEach(f -> System.out.println(f));
		
			fabHome.commitTransaction();
		}
		catch (RuntimeException e) {
			fabHome.rollbackTransaction();
		    throw e; // or display error message
		}
	}
	
	/**
	 * 9.Devuelve una lista con 2 fabricantes a partir del cuarto fabricante. El cuarto fabricante también se debe incluir en la respuesta.
	 */
	@Test
	void test9() {
	
		FabricanteHome fabHome = new FabricanteHome();
		
		try {
			fabHome.beginTransaction();
	
			List<Fabricante> listFab = fabHome.findAll();
					
			//TODO STREAMS
			List<String> lista2fab = listFab.stream().sorted(comparing(Fabricante::getNombre)).skip(3).limit(2).map(
					f -> "Name: " + f.getNombre() + ", Code: " + f.getCodigo()).toList();

			lista2fab.forEach(f -> System.out.println(f));
		
			fabHome.commitTransaction();
		}
		catch (RuntimeException e) {
			fabHome.rollbackTransaction();
		    throw e; // or display error message
		}
	}
	
	/**
	 * 10. Lista el nombre y el precio del producto más barato
	 */
	@Test
	void test10() {
	
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();		
						
			//TODO STREAMS
			List<String> productoMasBarato = listProd.stream().sorted(comparing(Producto::getPrecio)).limit(1).map(
					p -> "Nombre: " + p.getNombre() + ". Precio: " + p.getPrecio()).toList();

			productoMasBarato.forEach(System.out::println);
			//System.out.println(productoMasBarato);
			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 11. Lista el nombre y el precio del producto más caro
	 */
	@Test
	void test11() {
	
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();		
						
			//TODO STREAMS
			//REPITO TEST ANTERIOR Y SALTO HASTA LA ULTIMA POSICIÓN:
			//TAMBIEN SE PUEDE HACER ORDENANDO REVERSED Y USANDO LIMIT 1 COMO EN EL ANTERIOR TEST (¿QUIZAS MAS EFICIENTE?)
			List<String> productoMasCaro = listProd.stream().sorted(comparing(Producto::getPrecio)).skip(listProd.size()-1).map(
					p -> "Nombre: " + p.getNombre() + ". Precio: " + p.getPrecio()).toList();

			productoMasCaro.forEach(System.out::println);

			// OTRA FORMA MÁS EFICIENTE PARA PROTEGERNOS CONTRA UNA COLECCIÓN VACÍA:
			Optional<Producto> optionalProducto = listProd.stream().sorted(comparing(Producto::getPrecio).reversed()).findFirst();
			optionalProducto.ifPresent(producto -> System.out.println(producto.getNombre()));

			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 12. Lista el nombre de todos los productos del fabricante cuyo código de fabricante es igual a 2.
	 * 
	 */
	@Test
	void test12() {
	
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();		
						
			//TODO STREAMS
			List<String> productosFabricante2 = listProd.stream().filter(p -> p.getFabricante().getCodigo()==2).map(
					p -> "Cod Fabricante: " + p.getFabricante().getCodigo() + ", Producto: " + p.getNombre()).toList();

			productosFabricante2.forEach(System.out::println);
				
			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 13. Lista el nombre de los productos que tienen un precio menor o igual a 120€.
	 */
	@Test
	void test13() {
	
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();		
						
			//TODO STREAMS
			List<String> productosMenos120 = listProd.stream().filter(p -> p.getPrecio()<=120).map(
					p -> p.getNombre() + ", precio: " + p.getPrecio()).toList();

			productosMenos120.forEach(System.out::println);
				
			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 14. Lista los productos que tienen un precio mayor o igual a 400€.
	 */
	@Test
	void test14() {
	
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();		
						
			//TODO STREAMS
			List<String> productosMas400 = listProd.stream().filter(p -> p.getPrecio()>=400).map(
					p -> p.getNombre() + ", precio: " + p.getPrecio()).toList();

			productosMas400.forEach(System.out::println);
				
			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 15. Lista todos los productos que tengan un precio entre 80€ y 300€. 
	 */
	@Test
	void test15() {
	
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();	
			
			//TODO STREAMS
			List<String> productosEntre80y300 = listProd.stream().filter(p -> p.getPrecio()>=80 && p.getPrecio()<=300).map(
					p -> p.getNombre() + ", precio: " + p.getPrecio()).toList();

			productosEntre80y300.forEach(System.out::println);
				
			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 16. Lista todos los productos que tengan un precio mayor que 200€ y que el código de fabricante sea igual a 6.
	 */
	@Test
	void test16() {
	
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();		
						
			//TODO STREAMS
			List<String> productosFab6yMas200 = listProd.stream().filter(p -> p.getPrecio()>200 && p.getFabricante().getCodigo()==6).map(
					p -> p.getNombre() + ", precio: " + p.getPrecio() + "(cod Fab: " + p.getFabricante().getCodigo() + ")").toList();

			productosFab6yMas200.forEach(System.out::println);
				
			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 17. Lista todos los productos donde el código de fabricante sea 1, 3 o 5 utilizando un Set de codigos de fabricantes para filtrar.
	 */
	@Test
	void test17() {
	
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();		
			
			//TODO STREAMS
			Set<Integer> codigosSeleccionados = new HashSet<>();
			codigosSeleccionados.add(1);
			codigosSeleccionados.add(3);
			codigosSeleccionados.add(5);

			List<String> listaProdFab135 = listProd.stream().filter(p -> codigosSeleccionados.contains(p.getFabricante().getCodigo())).map(
					p -> p.getNombre() + ", precio: " + p.getPrecio() + " (cod Fab: " + p.getFabricante().getCodigo() + ")").toList();

			listaProdFab135.forEach(System.out::println);

			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 18. Lista el nombre y el precio de los productos en céntimos.
	 */
	@Test
	void test18() {
	
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();		
			
			//TODO STREAMS
			List<String> listaPrecioCentimos = listProd.stream().map(
					p->  "Nombre: " + p.getNombre() + ", Precio: " + p.getPrecio()*100 + " cents").toList();

			listaPrecioCentimos.forEach(System.out::println);
				
			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	
	/**
	 * 19. Lista los nombres de los fabricantes cuyo nombre empiece por la letra S
	 */
	@Test
	void test19() {
	
	
		FabricanteHome fabHome = new FabricanteHome();
		
		try {
			fabHome.beginTransaction();
	
			List<Fabricante> listFab = fabHome.findAll();
					
			//TODO STREAMS
			List<String> listaFabricantes = listFab.stream().filter(f -> f.getNombre().toLowerCase().startsWith("s")).map(
					f -> "Fabricante: " + f.getNombre()).toList();

			listaFabricantes.forEach(f -> System.out.println(f));
		
			fabHome.commitTransaction();
		}
		catch (RuntimeException e) {
			fabHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 20. Devuelve una lista con los productos que contienen la cadena Portátil en el nombre.
	 */
	@Test
	void test20() {


		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();
			
			//TODO STREAMS
			List<String> listaPortatiles = listProd.stream().filter(p -> p.getNombre().contains("Portátil")).map(
					Producto::getNombre).toList();

			listaPortatiles.forEach(System.out::println);

			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 21. Devuelve una lista con el nombre de todos los productos que contienen la cadena Monitor en el nombre y tienen un precio inferior a 215 €.
	 */
	@Test
	void test21() {
	
	
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();
			
			//TODO STREAMS
			List<String> listaMonitores = listProd.stream().filter(p -> p.getNombre().contains("Monitor") && p.getPrecio()<215).map(
					p -> p.getNombre() + ", precio: " + p.getPrecio()).toList();

			listaMonitores.forEach(System.out::println);
				
			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 22. Lista el nombre y el precio de todos los productos que tengan un precio mayor o igual a 180€. 
	 * Ordene el resultado en primer lugar por el precio (en orden descendente) y en segundo lugar por el nombre (en orden ascendente).
	 */
	@Test
	void test22() {
		
		
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();
			
			//TODO STREAMS
			List<String> productosMas180order = listProd.stream()
					.filter(p -> p.getPrecio()>=180)
					.sorted(comparing(Producto::getPrecio).reversed().thenComparing(comparing(Producto::getNombre)))
					.map(p -> p.getNombre() + ", precio: " + p.getPrecio())
					.toList();

			productosMas180order.forEach(System.out::println);
				
			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 23. Devuelve una lista con el nombre del producto, precio y nombre de fabricante de todos los productos de la base de datos. 
	 * Ordene el resultado por el nombre del fabricante, por orden alfabético.
	 */
	@Test
	void test23() {
		
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();
			
			//TODO STREAMS
			List<String> listaProductosPorFabricante = listProd.stream().sorted(comparing(p -> p.getFabricante().getNombre())).map(
					p -> p.getNombre() + ", precio: " + p.getPrecio() + "€, Fab: " + p.getFabricante().getNombre().toUpperCase()).toList();

			listaProductosPorFabricante.forEach(System.out::println);
			
			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 24. Devuelve el nombre del producto, su precio y el nombre de su fabricante, del producto más caro.
	 */
	@Test
	void test24() {
		
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();
			
			//TODO STREAMS
			Optional<String> productoMasCaro = listProd.stream().max(comparing(Producto::getPrecio)).map(
					p -> p.getNombre() + ", precio: " + p.getPrecio() + "€, Fab: " + p.getFabricante().getNombre());

			productoMasCaro.ifPresent(System.out::println);
			
			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 25. Devuelve una lista de todos los productos del fabricante Crucial que tengan un precio mayor que 200€.
	 */
	@Test
	void test25() {
		
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();
			
			//TODO STREAMS
			List<String> productosCrucialMas200 = listProd.stream()
					.filter(p -> p.getFabricante().getNombre().equalsIgnoreCase("crucial") && p.getPrecio()>200)
					.map(p -> p.getNombre() + ", precio: " + p.getPrecio() + "€, Fab: " + p.getFabricante().getNombre())
					.toList();

			productosCrucialMas200.forEach(System.out::println);
			
			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 26. Devuelve un listado con todos los productos de los fabricantes Asus, Hewlett-Packard y Seagate
	 */
	@Test
	void test26() {
		
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();
			
			//TODO STREAMS
			List<String> fabricantesSeleccionados = new ArrayList<>();
			fabricantesSeleccionados.add("asus");
			fabricantesSeleccionados.add("hewlett-packard");
			fabricantesSeleccionados.add("seagate");

			List<String> productosFabSeleccionados = listProd.stream()
					.filter(p -> fabricantesSeleccionados.contains(p.getFabricante().getNombre().toLowerCase()))
					.map(p -> p.getNombre() + ", precio: " + p.getPrecio() + "€, Fab: " + p.getFabricante().getNombre())
					.toList();

			productosFabSeleccionados.forEach(System.out::println);
			
			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 27. Devuelve un listado con el nombre de producto, precio y nombre de fabricante, de todos los productos que tengan un precio mayor o igual a 180€. 
	 * Ordene el resultado en primer lugar por el precio (en orden descendente) y en segundo lugar por el nombre.
	 * El listado debe mostrarse en formato tabla. Para ello, procesa las longitudes máximas de los diferentes campos a presentar y compensa mediante la inclusión de espacios en blanco.
	 * La salida debe quedar tabulada como sigue:

	Producto                Precio             Fabricante
	-----------------------------------------------------
	GeForce GTX 1080 Xtreme|611.5500000000001 |Crucial
	Portátil Yoga 520      |452.79            |Lenovo
	Portátil Ideapd 320    |359.64000000000004|Lenovo
	Monitor 27 LED Full HD |199.25190000000003|Asus

	 */		
	@Test
	void test27() {
		
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();

			List<Integer> medidasMax = new ArrayList<>();

			medidasMax.add(listProd.stream().mapToInt(p -> p.getNombre().length()).max().getAsInt());
			medidasMax.add(listProd.stream().mapToInt(p -> String.valueOf(p.getPrecio()).length()).max().getAsInt());
			medidasMax.add(listProd.stream().mapToInt(p -> p.getFabricante().getNombre().length()).max().getAsInt());

			// " ".repeat
			String tablaProductosMas180 = listProd.stream()
					.filter(p -> p.getPrecio()>=180)
					.sorted(comparing(Producto::getPrecio).reversed().thenComparing(Producto::getNombre))
					.map(p-> p.getNombre() + " ".repeat(medidasMax.get(0)-(p.getNombre().length())) +
							"|" + p.getPrecio() + " ".repeat(medidasMax.get(1)-((String.valueOf(p.getPrecio())).length())) +
							"|" + p.getFabricante().getNombre() + " ".repeat(medidasMax.get(2)-(p.getFabricante().getNombre().length())) + "\n").collect(joining());


			String header = "Producto                       Precio      Fabricante\n" +
					"-----------------------------------------------------\n";
			System.out.println("" + header + tablaProductosMas180.toString());

			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 28. Devuelve un listado de los nombres fabricantes que existen en la base de datos, junto con los nombres productos que tiene cada uno de ellos. 
	 * El listado deberá mostrar también aquellos fabricantes que no tienen productos asociados. 
	 * SÓLO SE PUEDEN UTILIZAR STREAM, NO PUEDE HABER BUCLES
	 * La salida debe queda como sigue:
Fabricante: Asus

            	Productos:
            	Monitor 27 LED Full HD
            	Monitor 24 LED Full HD

Fabricante: Lenovo

            	Productos:
            	Portátil Ideapd 320
            	Portátil Yoga 520

Fabricante: Hewlett-Packard

            	Productos:
            	Impresora HP Deskjet 3720
            	Impresora HP Laserjet Pro M26nw

Fabricante: Samsung

            	Productos:
            	Disco SSD 1 TB

Fabricante: Seagate

            	Productos:
            	Disco duro SATA3 1TB

Fabricante: Crucial

            	Productos:
            	GeForce GTX 1080 Xtreme
            	Memoria RAM DDR4 8GB

Fabricante: Gigabyte

            	Productos:
            	GeForce GTX 1050Ti

Fabricante: Huawei

            	Productos:


Fabricante: Xiaomi

            	Productos:

	 */
	@Test
	void test28() {
	
		FabricanteHome fabHome = new FabricanteHome();
		
		try {
			fabHome.beginTransaction();
	
			List<Fabricante> listFab = fabHome.findAll();

			// collect(joinning)
			List<String> tablaFabricantesyProductos = listFab.stream()
					.map(f -> "\nFabricante: " + f.getNombre() + "\n\n\tProductos:" + f.getProductos().stream().map(p -> "\n\t" + p.getNombre() + "").collect(joining())).toList();

			tablaFabricantesyProductos.forEach(System.out::println);

			fabHome.commitTransaction();
		}
		catch (RuntimeException e) {
			fabHome.rollbackTransaction();
		    throw e; // or display error message
		}
	}
	
	/**
	 * 29. Devuelve un listado donde sólo aparezcan aquellos fabricantes que no tienen ningún producto asociado.
	 */
	@Test
	void test29() {
	
		FabricanteHome fabHome = new FabricanteHome();
		
		try {
			fabHome.beginTransaction();
	
			List<Fabricante> listFab = fabHome.findAll();
					
			//TODO STREAMS
			List<String> listaEmptyFab = listFab.stream().filter(fabricante -> fabricante.getProductos().isEmpty()).map(f -> f.getNombre()).toList();
			listaEmptyFab.forEach(System.out::println);
								
			fabHome.commitTransaction();
		}
		catch (RuntimeException e) {
			fabHome.rollbackTransaction();
		    throw e; // or display error message
		}
	}
	
	/**
	 * 30. Calcula el número total de productos que hay en la tabla productos. Utiliza la api de stream.
	 */
	@Test
	void test30() {
	
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();		
						
			//TODO STREAMS
			long totalProductos = listProd.stream().count();
			System.out.println(totalProductos);
			
			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}

	
	/**
	 * 31. Calcula el número de fabricantes con productos, utilizando un stream de Productos.
	 */
	@Test
	void test31() {
	
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();		
						
			//TODO STREAMS
			long totalFabricantesProd = listProd.stream().map(p -> p.getFabricante()).distinct().count();
			System.out.println(totalFabricantesProd);
			
			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 32. Calcula la media del precio de todos los productos
	 */
	@Test
	void test32() {
	
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();		
						
			//TODO STREAMS
			double mediaPrecioProductos = listProd.stream().map(Producto::getPrecio).reduce(0.0, Double::sum)/listProd.size();
			System.out.println(mediaPrecioProductos);

			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
	}
	
	/**
	 * 33. Calcula el precio más barato de todos los productos. No se puede utilizar ordenación de stream.
	 */
	@Test
	void test33() {
	
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();		
						
			//TODO STREAMS
			double precioMasBarato = listProd.stream().min(comparing(Producto::getPrecio)).map(Producto::getPrecio).get();
			System.out.println(precioMasBarato);

			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 34. Calcula la suma de los precios de todos los productos.
	 */
	@Test
	void test34() {
		
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();		
						
			//TODO STREAMS
			double sumaPrecioProductos = listProd.stream().map(Producto::getPrecio).reduce(0.0, Double::sum);
			System.out.println(sumaPrecioProductos);
			
			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 35. Calcula el número de productos que tiene el fabricante Asus.
	 */
	@Test
	void test35() {
		
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();		
						
			//TODO STREAMS
			long numProductosAsus = listProd.stream()
					.filter(p -> p.getFabricante().getNombre().equalsIgnoreCase("asus")).count();

			System.out.println(numProductosAsus);
			
			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 36. Calcula la media del precio de todos los productos del fabricante Asus.
	 */
	@Test
	void test36() {
		
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();		
						
			//TODO STREAMS
			//PRECIO TOTAL PRODUCTOS ASUS
			double precioTotalProductosAsus = listProd.stream()
					.filter(p -> p.getFabricante().getNombre().equalsIgnoreCase("asus"))
					.map(Producto::getPrecio)
					.reduce(0.0, Double::sum);
			// NUMERO DE PRODUCTOS ASUS:
			long numTotalProductosAsus = listProd.stream()
					.filter(p -> p.getFabricante().getNombre().equalsIgnoreCase("asus"))
					.count();
			// FALTA LA DIVISION ENTRE EL NUMERO DE PRODUCTOS ENCONTRADOS
			double mediaPrecioProdAsus = precioTotalProductosAsus/numTotalProductosAsus;
			System.out.println(mediaPrecioProdAsus);
			
			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	
	/**
	 * 37. Muestra el precio máximo, precio mínimo, precio medio y el número total de productos que tiene el fabricante Crucial. 
	 *  Realízalo en 1 solo stream principal. Utiliza reduce con Double[] como "acumulador".
	 */
	@Test
	void test37() {
		
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();
						
			//TODO STREAMS
			DoubleSummaryStatistics crucialStatics = listProd.stream()
					.filter(p -> p.getFabricante().getNombre().equalsIgnoreCase("crucial"))
					.collect(summarizingDouble(Producto::getPrecio));

			System.out.println(crucialStatics);
			
			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 38. Muestra el número total de productos que tiene cada uno de los fabricantes. 
	 * El listado también debe incluir los fabricantes que no tienen ningún producto. 
	 * El resultado mostrará dos columnas, una con el nombre del fabricante y otra con el número de productos que tiene. 
	 * Ordene el resultado descendentemente por el número de productos. Utiliza String.format para la alineación de los nombres y las cantidades.
	 * La salida debe queda como sigue:
	 
     Fabricante     #Productos
-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*
           Asus              2
         Lenovo              2
Hewlett-Packard              2
        Samsung              1
        Seagate              1
        Crucial              2
       Gigabyte              1
         Huawei              0
         Xiaomi              0

	 */
	@Test
	void test38() {
	
		FabricanteHome fabHome = new FabricanteHome();
		
		try {
			fabHome.beginTransaction();
				
			List<Fabricante> listFab = fabHome.findAll();
				
			//TODO STREAMS

			/*
				██████╗░███████╗███╗░░██╗██████╗░██╗███████╗███╗░░██╗████████╗███████╗
				██╔══██╗██╔════╝████╗░██║██╔══██╗██║██╔════╝████╗░██║╚══██╔══╝██╔════╝
				██████╔╝█████╗░░██╔██╗██║██║░░██║██║█████╗░░██╔██╗██║░░░██║░░░█████╗░░
				██╔═══╝░██╔══╝░░██║╚████║██║░░██║██║██╔══╝░░██║╚████║░░░██║░░░██╔══╝░░
				██║░░░░░███████╗██║░╚███║██████╔╝██║███████╗██║░╚███║░░░██║░░░███████╗
				╚═╝░░░░░╚══════╝╚═╝░░╚══╝╚═════╝░╚═╝╚══════╝╚═╝░░╚══╝░░░╚═╝░░░╚══════╝
			*/

		
			fabHome.commitTransaction();
		}
		catch (RuntimeException e) {
			fabHome.rollbackTransaction();
		    throw e; // or display error message
		}
	}
	
	/**
	 * 39. Muestra el precio máximo, precio mínimo y precio medio de los productos de cada uno de los fabricantes. 
	 * El resultado mostrará el nombre del fabricante junto con los datos que se solicitan. Realízalo en 1 solo stream principal. Utiliza reduce con Double[] como "acumulador".
	 * Deben aparecer los fabricantes que no tienen productos.
	 */
	@Test
	void test39() {
	
		FabricanteHome fabHome = new FabricanteHome();
		
		try {
			fabHome.beginTransaction();
				
			List<Fabricante> listFab = fabHome.findAll();
				
			//TODO STREAMS

			/*

			List<String> resultados = listFab.stream()
					.map(fabricante -> fabricante.getProductos().stream()
							.mapToDouble(Producto::getPrecio)
							.reduce(new Double[]{Double.MAX_VALUE, Double.MIN_VALUE, 0.0, 0.0},
									(acc, precio) -> {
										acc[0] = Math.min(acc[0], precio); // Precio mínimo
										acc[1] = Math.max(acc[1], precio); // Precio máximo
										acc[2] += precio; // Suma para calcular el precio medio
										acc[3]++; // Contador para calcular el precio medio
										return acc;
									},
									(acc1, acc2) -> {
										acc1[0] = Math.min(acc1[0], acc2[0]); // Precio mínimo
										acc1[1] = Math.max(acc1[1], acc2[1]); // Precio máximo
										acc1[2] += acc2[2]; // Suma para calcular el precio medio
										acc1[3] += acc2[3]; // Contador para calcular el precio medio
										return acc1;
									}
							))
					.map(acc -> String.format("Fabricante: %s, Precio mínimo: %.2f €, Precio máximo: %.2f €, Precio medio: %.2f €",
							acc[0], acc[1], acc[2] / acc[3]))
					.collect(Collectors.toList());

			resultados.forEach(System.out.println);


				██████╗░███████╗███╗░░██╗██████╗░██╗███████╗███╗░░██╗████████╗███████╗
				██╔══██╗██╔════╝████╗░██║██╔══██╗██║██╔════╝████╗░██║╚══██╔══╝██╔════╝
				██████╔╝█████╗░░██╔██╗██║██║░░██║██║█████╗░░██╔██╗██║░░░██║░░░█████╗░░
				██╔═══╝░██╔══╝░░██║╚████║██║░░██║██║██╔══╝░░██║╚████║░░░██║░░░██╔══╝░░
				██║░░░░░███████╗██║░╚███║██████╔╝██║███████╗██║░╚███║░░░██║░░░███████╗
				╚═╝░░░░░╚══════╝╚═╝░░╚══╝╚═════╝░╚═╝╚══════╝╚═╝░░╚══╝░░░╚═╝░░░╚══════╝
			*/
		
			fabHome.commitTransaction();
		}
		catch (RuntimeException e) {
			fabHome.rollbackTransaction();
		    throw e; // or display error message
		}
	}
	
	/**
	 * 40. Muestra el precio máximo, precio mínimo, precio medio y el número total de productos de los fabricantes que tienen un precio medio superior a 200€. 
	 * No es necesario mostrar el nombre del fabricante, con el código del fabricante es suficiente.
	 */
	@Test
	void test40() {
	
		FabricanteHome fabHome = new FabricanteHome();
		
		try {
			fabHome.beginTransaction();
				
			List<Fabricante> listFab = fabHome.findAll();
				
			//TODO STREAMS

			/*
				██████╗░███████╗███╗░░██╗██████╗░██╗███████╗███╗░░██╗████████╗███████╗
				██╔══██╗██╔════╝████╗░██║██╔══██╗██║██╔════╝████╗░██║╚══██╔══╝██╔════╝
				██████╔╝█████╗░░██╔██╗██║██║░░██║██║█████╗░░██╔██╗██║░░░██║░░░█████╗░░
				██╔═══╝░██╔══╝░░██║╚████║██║░░██║██║██╔══╝░░██║╚████║░░░██║░░░██╔══╝░░
				██║░░░░░███████╗██║░╚███║██████╔╝██║███████╗██║░╚███║░░░██║░░░███████╗
				╚═╝░░░░░╚══════╝╚═╝░░╚══╝╚═════╝░╚═╝╚══════╝╚═╝░░╚══╝░░░╚═╝░░░╚══════╝
			*/

			/*

			Map<String, Object[]> resultados = listFab.stream()
					.filter(fabricante -> fabricante.getProductos().stream()
							.mapToDouble(Producto::getPrecio)
							.average()
							.orElse(0) > 200)
					.collect(Collectors.toMap(
							Fabricante::getCodigo,
							fabricante -> {
								double precioMinimo = fabricante.getProductos().stream()
										.mapToDouble(Producto::getPrecio)
										.min()
										.orElse(0);
								double precioMaximo = fabricante.getProductos().stream()
										.mapToDouble(Producto::getPrecio)
										.max()
										.orElse(0);
								double precioMedio = fabricante.getProductos().stream()
										.mapToDouble(Producto::getPrecio)
										.average()
										.orElse(0);
								long numeroTotalProductos = fabricante.getProductos().size();
								return new Object[]{precioMinimo, precioMaximo, precioMedio, numeroTotalProductos};
							}
					));

			resultados.forEach((codigo, valores) -> System.out.println(
					"Código del fabricante: " + codigo +
							", Precio mínimo: " + valores[0] +
							", Precio máximo: " + valores[1] +
							", Precio medio: " + valores[2] +
							", Número total de productos: " + valores[3]
			));



			 */
			fabHome.commitTransaction();
		}
		catch (RuntimeException e) {
			fabHome.rollbackTransaction();
		    throw e; // or display error message
		}
	}
	
	/**
	 * 41. Devuelve un listado con los nombres de los fabricantes que tienen 2 o más productos.
	 */
	@Test
	void test41() {
		
		FabricanteHome fabHome = new FabricanteHome();
		
		try {
			fabHome.beginTransaction();
				
			List<Fabricante> listFab = fabHome.findAll();
				
			//TODO STREAMS
			List<String> fabricantesMas2prod = listFab.stream().filter(f -> f.getProductos().size()>=2).map(Fabricante::getNombre).toList();

			fabricantesMas2prod.forEach(System.out::println);

			fabHome.commitTransaction();
		}
		catch (RuntimeException e) {
			fabHome.rollbackTransaction();
		    throw e; // or display error message
		}
	}
	
	/**
	 * 42. Devuelve un listado con los nombres de los fabricantes y el número de productos que tiene cada uno con un precio superior o igual a 220 €. 
	 * Ordenado de mayor a menor número de productos.
	 */
	@Test
	void test42() {
		
		FabricanteHome fabHome = new FabricanteHome();
		
		try {
			fabHome.beginTransaction();
				
			List<Fabricante> listFab = fabHome.findAll();
				
			//TODO STREAMS

			List<String> fabricantesConProductosCaros = listFab.stream()
					.collect(Collectors.toMap(
							Fabricante::getNombre,
							fabricante -> (int) fabricante.getProductos().stream()
									.filter(producto -> producto.getPrecio() >= 220)
									.count()
					))
					.entrySet()
					.stream()
					.sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
					.map(entry -> entry.getKey() + ": " + entry.getValue() + " productos")
					.collect(Collectors.toList());

			fabricantesConProductosCaros.forEach(System.out::println);

			fabHome.commitTransaction();
		}
		catch (RuntimeException e) {
			fabHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 43. Devuelve un listado con los nombres de los fabricantes donde la suma del precio de todos sus productos es superior a 1000 €
	 */
	@Test
	void test43() {
		
		FabricanteHome fabHome = new FabricanteHome();
		
		try {
			fabHome.beginTransaction();
				
			List<Fabricante> listFab = fabHome.findAll();
				
			//TODO STREAMS

			List<String> listaFabMAs1000 = listFab.stream()
					.filter(fabricante -> fabricante.getProductos().stream()
							.mapToDouble(Producto::getPrecio)
							.sum() > 1000)
					.map(Fabricante::getNombre)
					.collect(Collectors.toList());

			listaFabMAs1000.forEach(System.out::println);
		
			fabHome.commitTransaction();
		}
		catch (RuntimeException e) {
			fabHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 44. Devuelve un listado con los nombres de los fabricantes donde la suma del precio de todos sus productos es superior a 1000 €
	 * Ordenado de menor a mayor por cuantía de precio de los productos.
	 */
	@Test
	void test44() {
		
		FabricanteHome fabHome = new FabricanteHome();
		
		try {
			fabHome.beginTransaction();
				
			List<Fabricante> listFab = fabHome.findAll();
				
			//TODO STREAMS
			List<String> listaFabMAs1000ordenado = listFab.stream()
					.filter(fabricante -> fabricante.getProductos().stream()
							.mapToDouble(Producto::getPrecio)
							.sum() > 1000)
					.sorted((fab1, fab2) -> {
						double precioFab1 = fab1.getProductos().stream().mapToDouble(Producto::getPrecio).sum();
						double precioFab2 = fab2.getProductos().stream().mapToDouble(Producto::getPrecio).sum();
						return Double.compare(precioFab1, precioFab2);
					})
					.map(Fabricante::getNombre)
					.collect(Collectors.toList());

			listaFabMAs1000ordenado.forEach(System.out::println);
		
			fabHome.commitTransaction();
		}
		catch (RuntimeException e) {
			fabHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 45. Devuelve un listado con el nombre del producto más caro que tiene cada fabricante. 
	 * El resultado debe tener tres columnas: nombre del producto, precio y nombre del fabricante. 
	 * El resultado tiene que estar ordenado alfabéticamente de menor a mayor por el nombre del fabricante.
	 */
	@Test
	void test45() {
		
		FabricanteHome fabHome = new FabricanteHome();
		
		try {
			fabHome.beginTransaction();
				
			List<Fabricante> listFab = fabHome.findAll();
				
			//TODO STREAMS
			List<String> productosMasCarosPorFabricante = listFab.stream()
					.map(fabricante -> fabricante.getProductos().stream()
							.max((prod1, prod2) -> Double.compare(prod1.getPrecio(), prod2.getPrecio()))
							.map(producto -> producto.getNombre() + ", " + producto.getPrecio() + "€, " + fabricante.getNombre())
							.orElse("") // Si no hay productos, establece una cadena vacía
					)
					.sorted()
					.collect(Collectors.toList());

			productosMasCarosPorFabricante.forEach(System.out::println);
		
			fabHome.commitTransaction();
		}
		catch (RuntimeException e) {
			fabHome.rollbackTransaction();
		    throw e; // or display error message
		}
			
	}
	
	/**
	 * 46. Devuelve un listado de todos los productos que tienen un precio mayor o igual a la media de todos los productos de su mismo fabricante.
	 * Se ordenará por fabricante en orden alfabético ascendente y los productos de cada fabricante tendrán que estar ordenados por precio descendente.
	 */
	@Test
	void test46() {
		
		FabricanteHome fabHome = new FabricanteHome();
		
		try {
			fabHome.beginTransaction();
				
			List<Fabricante> listFab = fabHome.findAll();
				
			//TODO STREAMS
			Map<String, Double> precioMedioPorFabricante = listFab.stream()
					.collect(Collectors.toMap(
							Fabricante::getNombre,
							fabricante -> fabricante.getProductos().stream()
									.mapToDouble(Producto::getPrecio)
									.average()
									.orElse(0) // Si no hay productos, se establece el valor predeterminado como 0
					));

			List<Producto> productosConPrecioSuperiorAMedia = listFab.stream()
					.flatMap(fabricante -> fabricante.getProductos().stream()
							.filter(producto -> producto.getPrecio() >= precioMedioPorFabricante.get(fabricante.getNombre()))
							.sorted((prod1, prod2) -> Double.compare(prod2.getPrecio(), prod1.getPrecio()))
					)
					.collect(Collectors.toList());

			productosConPrecioSuperiorAMedia.forEach(producto -> System.out.println(
					"Fabricante: " + producto.getFabricante().getNombre() +
							", Producto: " + producto.getNombre() +
							", Precio: " + producto.getPrecio()
			));
		
			fabHome.commitTransaction();
		}
		catch (RuntimeException e) {
			fabHome.rollbackTransaction();
		    throw e; // or display error message
		}
			
	}
	
}

