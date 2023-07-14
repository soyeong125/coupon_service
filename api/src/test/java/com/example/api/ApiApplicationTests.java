package com.example.api;

import com.example.api.repository.CouponRepository;
import com.example.api.service.ApplyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ApiApplicationTests {

	@Autowired
	private ApplyService applyService;

	@Autowired
	private CouponRepository couponRepository;

	@Test
	void 한번만응모() {
		applyService.apply(1L);

		long count = couponRepository.count();

		assertThat(count).isEqualTo(1);
	}

	@Test
	public void 천명응모() throws InterruptedException {
		int threadCount = 1000;
		//병렬작업을 간단하게 할 수 있도록 도와주는 java api
		ExecutorService executorService = Executors.newFixedThreadPool(32);
		//다른 스레드에서 하는 작업을 기다려주는 클래스
		CountDownLatch latch =  new CountDownLatch(threadCount);

		for(int i = 0; i < threadCount ; i++){
			long userId = i;
			executorService.submit(() -> {
				try{
					applyService.apply(userId);
				} finally {
					latch.countDown();
				}
			});
		}

		latch.await();

		Thread.sleep(10000);

		long count =  couponRepository.count();
		assertThat(count).isEqualTo(100);
	}

	@Test
	public void 한명당_한개의_쿠폰발급() throws InterruptedException {
		int threadCount = 1000;
		//병렬작업을 간단하게 할 수 있도록 도와주는 java api
		ExecutorService executorService = Executors.newFixedThreadPool(32);
		//다른 스레드에서 하는 작업을 기다려주는 클래스
		CountDownLatch latch =  new CountDownLatch(threadCount);

		for(int i = 0; i < threadCount ; i++){
			long userId = i;
			executorService.submit(() -> {
				try{
					applyService.apply(1L); //1번 유저가 1000번 보낼 떄 1번만 쿠폰이 발급되는지 확인
				} finally {
					latch.countDown();
				}
			});
		}

		latch.await();

		Thread.sleep(10000);

		long count =  couponRepository.count();
		assertThat(count).isEqualTo(1);
	}

}
