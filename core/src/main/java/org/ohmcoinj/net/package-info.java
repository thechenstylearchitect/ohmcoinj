/**
 * Classes handling low level network management using either NIO (async io) or older style blocking sockets (useful for
 * using SOCKS proxies, Tor, SSL etc). The code in this package implements a simple network abstraction a little like
 * what the Netty library provides, but with only what ohmcoinj needs.
 */
package org.ohmcoinj.net;