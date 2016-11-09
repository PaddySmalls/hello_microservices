
# Hello Microservices!

## Motivation

Distributed systems is a highly interesting and exciting topic. There're so many papers,
books and blogs out there covering different aspects of resilient, highly available as well as fault
tolerant systems or applications. If you're like me, you really enjoy plunging into all this.
However, all theory is gray. Therefore, I've decided to get my hands dirty while exploring
distributed systems in a practical manner. What you see here is the result of my work over the past few weeks.


## So, what is this project about?
During the previous winter semester, together with two fellow students I worked on a project which was about 
getting a Docker Swarm up and running on a cluster of several RaspberryPis. After we had finished, I thought that giving up our
freshly established cluster would be a pity. Though, I decided on building on top of that platform
and using it as a foundation for developing and deploying a microservices-style demo application. My plan was
to get in touch with some distributed systems principles, patterns as well as their practical
implementations in the first place. The upshot of these efforts is a small application which I simply call "Weather Service".
It consists of three individual components:

1) __Weather Data Producer__:<br/>
 This first component is a RESTful web service which takes a zip code as well
 as an optional country code as input and queries the OpenWeatherMap API for the latest weather data
 according to the provided parameters. 
 
2) __Weather Data Consumer__:<br/>
The second service offers a simple GUI which accepts a zip code and a country
code as user input, which is then transferred to the _Weather Data Producer_ by means of an
HTTP GET request. Afterwards, the data received as the payload of the HTTP response gets displayed 
on the GUI. 

3) __Service Discovery Application__:<br/>
In order to enable the consumer service to "discover" the producer instances within our local
network without actually knowing their location, we need a service discovery. The purpose and the
advantage of such a component is discussed in the next section.


On top of that, another goal should be to establish a highly automated CI/CD infrastructure. In a nutshell,
the resulting system was planned to do the following:

   + Check out the latest code base from VCS (Github in this case).
   + Compile and package the application as a single JAR.
   + Build a Docker Image by means of a module-dependent Dockerfile.
   + As soon as the image has been built, push it to a local Docker Registry.


## Patterns and Frameworks

In order to keep the project's complexity manageable, I chose three distributed system patterns which I wanted
to focus on and which I'd like to shortly introduce below. I also made the decision to not unnecessarily implement
any tools on my own, since there're already many well-proven tools out there which have been developed by people who know
what they're doing. In particular, I made use of Netflix' open source solutions, which I will also describe 
in a minute. So let's straightly get into the patterns I applied for my Weather Service:

1) __Service Discovery__:<br/>
In order to end up with a scalable and reliable application, we need our services to be loosely coupled.
What that means is that we don't want our consumer service to be bound to a hard coded IP address and port for the
producer service. Instead, every producer service instance available will register with our service
discovery application, where it can be looked up and therefore accessed by the consumer app. As a consequence, we 
can arbitrarily add and remove producer service instances running on different IPs and ports without breaking
clients.<br/>

2) __Circuit Breaker and Failover__:<br/>
With distributed systems, having servers and applications crashing and going down is rather standard than an exception.
When relying on a blocking communication protocol like HTTP requires an application to protect against starvation. What
that means is that if multiple services depend on each other, a single server being slow or entirely crashing will bind
all resources (e.g. threads), causing the whole system to come to a sudden halt. In order to avoid such a scenario, each
dependency an application declares on a remote service has to be backed by what can be called a lock as well as a thread
pool. Having a server thread passing control to a dedicated request thread from a fix-sized pool when a remote service
gets called comes with many advantages:

     + Imagine a client wants to contact a server which is temporarily or permanently down. With the client re-trying
      over and over again, it doesn't take long until a mass of threads idles, waiting for the server to respond. In the 
      worst case, the client runs out of memory and gets killed by the operating system. By means of a thread pool 
      per service we can define a maximum number of threads which are allowed to be in use a the same time. Remote calls
      will simply be rejected if there's no thread available in the pool.
      
     + In case a remote service stays down permanently, keep on sending requests is not the best way in order to give 
     the crashed server a chance to recover. A so-called "circuit breaker" blocks every call on a service in case the
     error rate exceeds a certain threshold. At the same time, the remote server gets pinged in constant intervals.
     Assuming that the crashed server finally comes back to live and responds to these messages over a certain 
     amount of time, the circuit breaker can decide to re-open the gate, letting requests pass again.
      
     + Another advantage of this approach is that an unreachable remote service must not necessarily in a
      client request simply being rejected. Instead, there's also the possibility to execute a "fallback" procedure
      if either a service is unavailable or the circuit breaker is open. For example, a reasonable operation within a
      fallback could be to fetch old data from a cache and send it to the client. In general, this approach is 
      called "failover". The concrete failover behavior that makes sense for an application mainly depends on 
      its use case.
      
      A very popular tool which exactly tackles these requirements is _Hystrix_ by Netflix. It provides a 
      a rich and comprehensible API for easily wrapping remote calls in _HystrixCommands_ and letting the framework
      take care of all the issues described above. Consider the docs of the _Weather Producer Service_ as well as 
      the _Weather Consumer_ to learn more on how Hystix has been applied in the context of this project.
    
    



![](/screenshots/consumer_gui.png)

![](/screenshots/consumer_gui_2.png)

![](/screenshots/failover_demo.png)

![](/screenshots/eureka.png)
  