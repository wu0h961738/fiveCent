package com.venom.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service(value="awardPools")
public class RenderPools {

    Lock lock = new ReentrantLock();
    Condition queueNotFull = lock.newCondition();
    Condition queueNotEmpty = lock.newCondition();
    private List<Integer> queue = new ArrayList<>();

    public Map<String, Object> getAll(){
        return null;
    }

    public Map<String, Object> get(String poolId){

        return null;
    }

    public List<Integer> getPoolInfo(){
        return this.queue;
    }

    public String create(String poolCode){
        return null;
    }
    public String delete(String poolCode, Map<String, Object> ticketObj){
        lock.lock();
        try{
            while(queue.size() == 0){
                System.out.println("pool was empty.");
                queueNotEmpty.await();
            }
            queue.remove(queue.size()-1);
            System.out.println("remove one item from pool.");
            queueNotFull.signal();
        } catch(Exception e){
            e.printStackTrace();
        } finally{
            lock.unlock();
        }
        return "delete from pool successfully.";
    }

    public String put(String poolCode, Map<String, Object> ticketObj) throws InterruptedException {
        lock.lock();
//        if(!lock.tryLock(200, TimeUnit.MILLISECONDS)){ //TODO 不曉得為什麼都不會有取鎖失敗的情況發生
//            return "got lock failed.";
//        }
        try{
            while(queue.size() == 3){
                System.out.println("pool was full.");
                queueNotFull.await();
            }
            queue.add(6);
            System.out.println("put an item into the pool.");
            queueNotEmpty.signal();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            lock.unlock();
        }
        return "put into pool successfully.";
    }

    private Map<String, Object> getByClientId(String clientCode){
        return null;
    }

    private void deleteByTicketCode(){

    }

    private void putSinglePool(){

    }

    private void putMultiPool(){

    }
}
