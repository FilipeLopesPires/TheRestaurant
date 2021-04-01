# The Restaurant
Parallel and Distributed Implementations of 'The Restaurant Problem'

## Description

This project allows the deployment of a simulated restaurant with either parallel or distributed execution of all entities.
The restaurant consist of 3 areas:
- the Table Area
- the Bar Area 
- the Kitchen Area

There are also 3 types of entities:
- the Students, which are restaurant clients 
- the Waiter
- the Chef

## Repository Structure

/parallel - initial parallel version, using Java Threads and Monitors.

/distributed - distributed version, using Java Sockets and Stubs.

/distributed-xml - truly distributed version, multiple machine deployment ready.

/diagrams - interaction and lifecycle diagrams for each implementation.

## The Parallel Restaurant

The parallel version resorts to Threading and access to Shared Regions through Monitors.
A single process is deployed and a thread is assigned to each entity instance.
Each area consists of a shared region and there is a general repository to control entity status and log activity.

This version is appropriate to explain in a simple and intuitive way how the restaurant works and how each entity must behave in order for the simulation to remain consistent.
On the diagrams directory we provide some visual representations that aid in understanding each entity's lifecycle and the interactions that occur.

## The Distributed Restaurant

The distributed version resorts to multiple java processes that communicate with each other through well established interfaces and predefined sockets.
Each shared region has a client side and a server side, while entities only need a client side and the general repository a server side.
Visual aids are also provided.

## The Distributed XML Restaurant

What differs from the distributed version is the fact that, in this implementation, the processes run on separate machines while still communicating with each other.
A shell script is provided for propagation among machines and visual aids are once again made available for further analysis.

## Authors

The authors of this repository are Filipe Pires and Isaac dos Anjos, and the project was developed for the Distributed Computation Course of the licenciate's degree in Informatics Engineering of the University of Aveiro.

For further information, please contact us at filipesnetopires@ua.pt.
