Tycho helper plugins
=====================

These plugins are intended to make it easier to work with Tycho based builds. There are several basic functionalities implemented:

* A tycho project nature to distinguish tycho based projects from other Maven projects
* An Eclipse builder to validate that the key POM and manifest attributes are in sync (mark as error in POM/MANIFEST if not)
    * POM `artifactId` = Manifest `Bundle-SymbolicName`
    * POM `version` = Manifest `Bundle-Version`
    * POM `version` = `*-SNAPSHOT` then Manifest `Bundle-Version` = `*.qualifier`
    * (optional) POM `name` = Manifest `Bundle-Name`
* Quick fix for above errors to sync from/to Manifest
* Project/Workspace preference page(s) to control ERROR/WARNING level for above markers
* A project context menu item to "enable tycho" on a PDE project
    * Add the Maven project nature etc.
    * Run the POM generator on the project (to create the POM)
    * Add the tycho project nature (to enable support of this plugin)

Installation
------------

Coming soon.

Create a build @ CloudBees

License
-------

These plugins are licensed under the [Eclipse Public License v1.0](http://www.eclipse.org/legal/epl-v10.html).

As you might expect these plugins use Maven/Tycho for their build.

