package com.aol.cyclops.streams.streamable;

import static com.aol.cyclops.control.Streamable.of;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.jooq.lambda.tuple.Tuple2;
import org.jooq.lambda.tuple.Tuple3;
import org.jooq.lambda.tuple.Tuple4;
import org.junit.Before;
import org.junit.Test;

import com.aol.cyclops.control.Streamable;

public class ZippingTest {
	Streamable<Integer> empty;
	Streamable<Integer> nonEmpty;

	@Before
	public void setup(){
		empty = of();
		nonEmpty = Streamable.of(1);
	}

	
	@Test
	public void zip(){
		List<Tuple2<Integer,Integer>> list =
				of(1,2,3,4,5,6).zip(of(100,200,300,400))
												.peek(it -> System.out.println(it))
												
												.collect(Collectors.toList());
		System.out.println(list);
		
		List<Integer> right = list.stream().map(t -> t.v2).collect(Collectors.toList());
		
		assertThat(right,hasItem(100));
		assertThat(right,hasItem(200));
		assertThat(right,hasItem(300));
		assertThat(right,hasItem(400));
		
		List<Integer> left = list.stream().map(t -> t.v1).collect(Collectors.toList());
		assertThat(Arrays.asList(1,2,3,4,5,6),hasItem(left.get(0)));
		
		
	}
	@Test
	public void zip3(){
		List<Tuple3<Integer,Integer,Character>> list =
				of(1,2,3,4,5,6).zip3(of(100,200,300,400),of('a','b','c'))
												.peek(it -> System.out.println(it))
												
												.collect(Collectors.toList());
		
		System.out.println(list);
		List<Integer> right = list.stream().map(t -> t.v2).collect(Collectors.toList());
		assertThat(right,hasItem(100));
		assertThat(right,hasItem(200));
		assertThat(right,hasItem(300));
		assertThat(right,not(hasItem(400)));
		
		List<Integer> left = list.stream().map(t -> t.v1).collect(Collectors.toList());
		assertThat(Arrays.asList(1,2,3,4,5,6),hasItem(left.get(0)));
		
		List<Character> three = list.stream().map(t -> t.v3).collect(Collectors.toList());
		assertThat(Arrays.asList('a','b','c'),hasItem(three.get(0)));
		
		
	}
	@Test
	public void zip4(){
		List<Tuple4<Integer,Integer,Character,String>> list =
				of(1,2,3,4,5,6).zip4(of(100,200,300,400),of('a','b','c'),of("hello","world"))
												.peek(it -> System.out.println(it))
												
												.collect(Collectors.toList());
		System.out.println(list);
		List<Integer> right = list.stream().map(t -> t.v2).collect(Collectors.toList());
		assertThat(right,hasItem(100));
		assertThat(right,hasItem(200));
		assertThat(right,not(hasItem(300)));
		assertThat(right,not(hasItem(400)));
		
		List<Integer> left = list.stream().map(t -> t.v1).collect(Collectors.toList());
		assertThat(Arrays.asList(1,2,3,4,5,6),hasItem(left.get(0)));
		
		List<Character> three = list.stream().map(t -> t.v3).collect(Collectors.toList());
		assertThat(Arrays.asList('a','b','c'),hasItem(three.get(0)));
	
		List<String> four = list.stream().map(t -> t.v4).collect(Collectors.toList());
		assertThat(Arrays.asList("hello","world"),hasItem(four.get(0)));
		
		
	}
	
	@Test
	public void zip2of(){
		
		List<Tuple2<Integer,Integer>> list =of(1,2,3,4,5,6)
											.zip(of(100,200,300,400))
											.peek(it -> System.out.println(it))
											.collect(Collectors.toList());
				
	
		List<Integer> right = list.stream().map(t -> t.v2).collect(Collectors.toList());
		assertThat(right,hasItem(100));
		assertThat(right,hasItem(200));
		assertThat(right,hasItem(300));
		assertThat(right,hasItem(400));
		
		List<Integer> left = list.stream().map(t -> t.v1).collect(Collectors.toList());
		assertThat(Arrays.asList(1,2,3,4,5,6),hasItem(left.get(0)));

	}
	@Test
	public void zipInOrder(){
		
		List<Tuple2<Integer,Integer>> list =  of(1,2,3,4,5,6)
													.zip( of(100,200,300,400))
													.collect(Collectors.toList());
		
		assertThat(asList(1,2,3,4,5,6),hasItem(list.get(0).v1));
		assertThat(asList(100,200,300,400),hasItem(list.get(0).v2));
		
		
		
	}

	@Test
	public void zipEmpty() throws Exception {
		
		
		final Streamable<Integer> zipped = empty.zip(Streamable.<Integer>of(), (a, b) -> a + b);
		assertTrue(zipped.collect(Collectors.toList()).isEmpty());
	}

	@Test
	public void shouldReturnEmptySeqWhenZipEmptyWithNonEmpty() throws Exception {
		
		
		
		final Streamable<Integer> zipped = empty.zip(nonEmpty, (a, b) -> a + b);
		assertTrue(zipped.collect(Collectors.toList()).isEmpty());
	}

	@Test
	public void shouldReturnEmptySeqWhenZipNonEmptyWithEmpty() throws Exception {
		
		
		final Streamable<Integer> zipped = nonEmpty.zip(empty, (a, b) -> a + b);

		
		assertTrue(zipped.collect(Collectors.toList()).isEmpty());
	}

	@Test
	public void shouldZipTwoFiniteSequencesOfSameSize() throws Exception {
		
		final Streamable<String> first = of("A", "B", "C");
		final Streamable<Integer> second = of(1, 2, 3);

		
		final Streamable<String> zipped = first.zip(second, (a, b) -> a + b);

		
		assertThat(zipped.collect(Collectors.toList()).size(),is(3));
	}

	

	@Test
	public void shouldTrimSecondFixedSeqIfLonger() throws Exception {
		final Streamable<String> first = of("A", "B", "C");
		final Streamable<Integer> second = of(1, 2, 3, 4);

		
		final Streamable<String> zipped = first.zip(second, (a, b) -> a + b);

		assertThat(zipped.collect(Collectors.toList()).size(),is(3));
	}

	@Test
	public void shouldTrimFirstFixedSeqIfLonger() throws Exception {
		final Streamable<String> first = of("A", "B", "C","D");
		final Streamable<Integer> second = of(1, 2, 3);
		final Streamable<String> zipped = first.zip(second, (a, b) -> a + b);

		
		assertThat(zipped.collect(Collectors.toList()).size(),equalTo(3));
	}

	@Test
	public void testZipDifferingLength() {
		List<Tuple2<Integer, String>> list = of(1, 2).zip(of("a", "b", "c", "d")).toList();

		assertEquals(2, list.size());
		assertTrue(asList(1, 2).contains(list.get(0).v1));
		assertTrue("" + list.get(1).v2, asList(1, 2).contains(list.get(1).v1));
		assertTrue(asList("a", "b", "c", "d").contains(list.get(0).v2));
		assertTrue(asList("a", "b", "c", "d").contains(list.get(1).v2));

	}

	
	@Test
	public void shouldTrimSecondFixedSeqIfLongerStream() throws Exception {
		final Streamable<String> first = of("A", "B", "C");
		final Streamable<Integer> second = of(1, 2, 3, 4);

		
		final Streamable<String> zipped = first.zip(second, (a, b) -> a + b);

		assertThat(zipped.collect(Collectors.toList()).size(),is(3));
	}

	@Test
	public void shouldTrimFirstFixedSeqIfLongerStream() throws Exception {
		final Streamable<String> first = of("A", "B", "C","D");
		final Streamable<Integer> second = of(1, 2, 3);
		final Streamable<String> zipped = first.zip(second, (a, b) -> a + b);

		
		assertThat(zipped.collect(Collectors.toList()).size(),equalTo(3));
	}

	@Test
	public void testZipDifferingLengthStream() {
		List<Tuple2<Integer, String>> list = of(1, 2).zip(of("a", "b", "c", "d")).toList();

		assertEquals(2, list.size());
		assertTrue(asList(1, 2).contains(list.get(0).v1));
		assertTrue("" + list.get(1).v2, asList(1, 2).contains(list.get(1).v1));
		assertTrue(asList("a", "b", "c", "d").contains(list.get(0).v2));
		assertTrue(asList("a", "b", "c", "d").contains(list.get(1).v2));

	}

	@Test
	public void shouldTrimSecondFixedSeqIfLongerSequence() throws Exception {
		final Streamable<String> first = of("A", "B", "C");
		final Streamable<Integer> second = of(1, 2, 3, 4);

		
		final Streamable<String> zipped = first.zip(second, (a, b) -> a + b);

		assertThat(zipped.collect(Collectors.toList()).size(),is(3));
	}

	@Test
	public void shouldTrimFirstFixedSeqIfLongerSequence() throws Exception {
		final Streamable<String> first = of("A", "B", "C","D");
		final Streamable<Integer> second = of(1, 2, 3);
		final Streamable<String> zipped = first.zip(second, (a, b) -> a + b);

		
		assertThat(zipped.collect(Collectors.toList()).size(),equalTo(3));
	}

	
	@Test
	public void testZipWithIndex() {
		assertEquals(asList(), of().zipWithIndex().toList());

		assertThat(of("a").zipWithIndex().map(t -> t.v2).findFirst().get(), is(0l));
		assertEquals(asList(new Tuple2("a", 0L)), of("a").zipWithIndex().toList());

	}

	@Test
	public void testUnzip() {

		Supplier<Streamable<Tuple2<Integer, String>>> s = () -> of(new Tuple2(1, "a"), new Tuple2(2, "b"), new Tuple2(3, "c"));

		Tuple2<Streamable<Integer>, Streamable<String>> u1 = Streamable.unzip(s.get());

		assertTrue(u1.v1.toList().containsAll(Arrays.asList(1, 2, 3)));

		assertTrue(u1.v2.toList().containsAll(asList("a", "b", "c")));

	}

	@Test
	public void testUnzipWithLimits() {

		Supplier<Streamable<Tuple2<Integer, String>>> s = () -> of(new Tuple2(1, "a"), new Tuple2(2, "b"), new Tuple2(3, "c"));

		Tuple2<Streamable<Integer>, Streamable<String>> u1 = Streamable.unzip(s.get());

		assertTrue(u1.v1.limit(2).toList().containsAll(Arrays.asList(1, 2)));

		assertTrue(u1.v2.toList().containsAll(asList("a", "b", "c")));

	}

	@Test
	public void testUnzip3WithLimits() {

		Supplier<Streamable<Tuple3<Integer, String, Long>>> s = () -> of(new Tuple3(1, "a", 2l), new Tuple3(2, "b", 3l), new Tuple3(3, "c", 4l));

		Tuple3<Streamable<Integer>, Streamable<String>, Streamable<Long>> u1 = Streamable.unzip3(s.get());

		assertTrue(u1.v1.limit(1).toList().containsAll(Arrays.asList(1)));

		assertTrue(u1.v2.limit(2).toList().containsAll(asList("a", "b")));
		assertTrue(u1.v3.toList().containsAll(asList(2l, 3l, 4l)));

	}

	@Test
	public void testUnzip3() {

		Supplier<Streamable<Tuple3<Integer, String, Long>>> s = () -> of(new Tuple3(1, "a", 2l), new Tuple3(2, "b", 3l), new Tuple3(3, "c", 4l));

		Tuple3<Streamable<Integer>, Streamable<String>, Streamable<Long>> u1 = Streamable.unzip3(s.get());

		assertTrue(u1.v1.toList().containsAll(Arrays.asList(1, 2, 3)));

		assertTrue(u1.v2.toList().containsAll(asList("a", "b", "c")));
		assertTrue(u1.v3.toList().containsAll(asList(2l, 3l, 4l)));

	}

	@Test
	public void testUnzip4() {

		Supplier<Streamable<Tuple4<Integer, String, Long, Character>>> s = () -> of(new Tuple4(1, "a", 2l, 'z'), new Tuple4(2, "b", 3l, 'y'), new Tuple4(3, "c",
				4l, 'x'));

		Tuple4<Streamable<Integer>, Streamable<String>, Streamable<Long>, Streamable<Character>> u1 = Streamable.unzip4(s.get());

		assertTrue(u1.v1.toList().containsAll(Arrays.asList(1, 2, 3)));

		assertTrue(u1.v2.toList().containsAll(asList("a", "b", "c")));

		assertTrue(u1.v3.toList().containsAll(asList(2l, 3l, 4l)));
		assertTrue(u1.v4.toList().containsAll(asList('z', 'y', 'x')));

	}

	@Test
	public void testUnzip4WithLimits() {

		Supplier<Streamable<Tuple4<Integer, String, Long, Character>>> s = () -> of(new Tuple4(1, "a", 2l, 'z'), new Tuple4(2, "b", 3l, 'y'), new Tuple4(3, "c",
				4l, 'x'));

		Tuple4<Streamable<Integer>, Streamable<String>, Streamable<Long>, Streamable<Character>> u1 = Streamable.unzip4(s.get());

		assertTrue(u1.v1.limit(1).toList().containsAll(Arrays.asList(1)));

		assertTrue(u1.v2.limit(2).toList().containsAll(asList("a", "b")));

		assertTrue(u1.v3.limit(3).toList().containsAll(asList(2l, 3l, 4l)));
		assertTrue(u1.v4.limit(4).toList().containsAll(asList('z', 'y', 'x')));

	}

}
