# astro

This is a small java program to do some very stupid orbit calculations (n-body problem). I wanted to be able to see what might happen when adding objects or delta-v to various systems, so I wrote this to do it.

I say "stuipid" because it does a lot of things wrong to keep it simple. It's single threaded, it doesn't modify the "tick" constant (though that's configurable), it doesn't do any error calculation or correction, it doesn't do collisions or handle very close objects cleanly (tick is too large for that generally), etc.

For watching simple geo-scale orbits, though, this worked great.
