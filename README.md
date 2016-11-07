
# Hello Microservices!

## Motivation

Distributed Systems is a highly interesting and exciting topic. There're so many papers,
books and blogs out there covering different aspects of resilient, highly available as well as fault
tolerant systems or applications. If you're like me, you really enjoy plunging into all this.
However, all theory is gray. Therefore, I've decided to get my hands dirty while exploring
Distributed Systems in a practical manner. What you see here is the result of my work over the past few weeks.


## So, what is this project about?
During the previous winter semester, together with two fellow students I worked on a project which was about 
getting a Docker Swarm up and running on a cluster of six RaspberryPis. After we had finished, I thought that giving up our
freshly established cluster would be a pity. Though, I decided on building on top of that platform
by using it as a foundation for developing and deploying a microservices-style demo application. By doing so, my plan was
to come in touch with several distributed systems principles, patterns as well as their practical
implementations in the first place. The upshot of these efforts is a small application which I simply call "Weather Service".
It consists  of three individual components:

1) __Weather Data Producer__:<br/>
 This first component is a RESTful web service which takes a zip code as well
 as an optional country code as input and queries the OpenWeatherMap API for the latest weather data
 according to the provided parameters. 
 
2) __Weather Data Consumer__:<br/>
The second service offers a simple GUI which accepts a zip code as well as a country
code as user input. The given input is then transferred to the _Weather Data Producer_ by means of an
HTTP GET request. Afterwards, the HTTP response payload received gets displayed on the GUI. 

3) __Service Discovery Application__:<br/>
In order to end up with a scalable and reliable application, we need our services to be loosely coupled.
What that means is that we don't want our consumer service to be bound to a hard coded IP address and port for the
producer service. Instead, every producer service instance available will register with our service
discovery application, where it can be looked up and therefore accessed by the consumer app. As a consequence, we 
can arbitrarily add and remove producer service instances running on different IPs and ports without breaking
clients.


![](/screenshots/consumer_gui.png)

![](/screenshots/consumer_gui_2.png)

![](/screenshots/failover_demo.png)

![](/screenshots/eureka.png)
  