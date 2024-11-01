<!-- Uncomment the shield below once the repo is made public -->
<!-- [![All Contributors](https://img.shields.io/github/all-contributors/processing/processing4?color=ee8449)](#contributors) -->

<img src="https://processing.org/favicon.svg" width="250">

Processing is a flexible software sketchbook and a programming language designed for learning how to code.

This repository contains the source code for the [Processing](https://processing.org/) project for people who want to help improve the code.

## Using Processing

If you're interested in *using* Processing, head over to the [download page](https://processing.org/download), or read more about the project on the [Processing website](https://processing.org/). There are also several [tutorials](https://processing.org/tutorials) that provide a helpful introduction. They are complemented by hundreds of examples that are included with the software itself.

## Getting Help 
For assistance with your own sketches, projects, or code, please post your question on the Processing forum: https://discourse.processing.org/. Our community is full of experienced developers and knowledgeable users who are eager to help. Before you post, please take a moment to read the [guidelines on asking questions](https://discourse.processing.org/t/guidelines-asking-questions/2147) to make sure you get the best possible help. We’re incredibly grateful for the support and knowledge shared by everyone on the forum over the years.

## Contributing to Processing
We welcome new contributors. If you want to fix a bug that's been bothering you or want to give back to the project, you're in the right place! Please see our [CONTRIBUTING.md](CONTRIBUTING.md) for detailed guidelines on how to contribute.

## Expected Behavior 
Remember, Processing is a labor of love, run in large part by volunteers, and offered free of charge. We're here because we believe in this community and genuinely enjoy contributing to it. We always welcome constructive feedback. Just keep it friendly and helpful, please! For more tips on how to communicate within the project, take a peek at our [Code of Conduct](https://github.com/processing/processing4-carbon-aug-19?tab=coc-ov-file).

## Building Processing

Building Processing locally on your machine will let you troubleshoot and make sure your contributions work as intended before submitting them to this repository. It also gives you the flexibility to experiment and learn more about how Processing is structured.

For a quick start: 
1. Fork and clone the repository.
1. Open it in IntelliJ IDEA.
1. Install the required [Ant plugin](https://plugins.jetbrains.com/plugin/23025-ant).
1. Hit Run.

For more information and detailed instructions, follow our [How to Build Processing](build/README.md) guide.

## About the Processing 4.0 release

We've moved to a new repository for the 4.0 release so that we could cull a lot of the accumulated mess of the last 20 years. This made `git clone` (and most other `git` operations) a lot faster.

For a detailed list of changes relevant to developers working on this repository, and changes that may impact Library, Mode, or Tool development, please refer to [CHANGELOG.md](CHANGELOG.md). The full list of changes can be seen in [the release notes for each version](build/shared/revisions.md).

Processing 4 has [important updates](wiki/Changes-in-4.0) that prepare the platform for its future. Most significantly, this includes the move to Java 17 as well as major changes to the range of platforms we support (Apple Silicon! Raspberry Pi on 32- and 64-bit ARM!)

With any luck, many changes should be transparent to most users, in spite of how much is updated behind the scenes. More immediately visible changes include major work on the UI, including “themes” and the ability to change how sketches are named by default.

As with all releases, we did [everything possible](https://twitter.com/ben_fry/status/1426282574683516928) to avoid breaking API. However, there were still tweaks that we had to make. We tried to keep them minor. Our goal is stability, and keeping everyone's code running.

## Contact Information
For technical support or troubleshooting with your project, please post on the [Processing Forum](https://discourse.processing.org/).

For bug reports or feature requests, please [create an issue](https://github.com/processing/processing4/issues).

For non-technical inquiries, here’s how to get in touch:

- For press inquiries, general information about the Processing software, or other non-technical questions, contact [hello@processing.org](mailto:hello@processing.org).
- For anything related to the Processing Foundation or broader topics beyond the software, please reach out to [foundation@processingfoundation.org](mailto:foundation@processingfoundation.org).

## License & Copyright

- The **core library** is licensed under the GNU Lesser General Public License version 2.1 ([LGPL-2.1](https://www.gnu.org/licenses/old-licenses/lgpl-2.1.en.html)).
- Everything else including the **PDE** is licensed under the GNU General Public License version 2 ([GPL-2.0](https://www.gnu.org/licenses/old-licenses/gpl-2.0.html)).
- The **reference**, including the JavaDoc comments, is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License ([CC-BY-NC-SA-4.0](https://creativecommons.org/licenses/by-nc-sa/4.0/)).

For complete licensing information about the Processing core library and software, see [LICENSE.md](LICENSE.md)

For licensing information about the Processing website see the [processing-website README](https://github.com/processing/processing-website/blob/main/README.md#licenses).

Copyright (c) 2015-now The Processing Foundation

## Contributors

<!-- ALL-CONTRIBUTORS-LIST:START - Do not remove or modify this section -->
<!-- prettier-ignore-start -->
<!-- markdownlint-disable -->
<table>
  <tbody>
    <tr>
      <td align="center" valign="top" width="16.66%"><a href="https://fathom.info"><img src="https://avatars.githubusercontent.com/u/1623101?v=4?s=120" width="120px;" alt="Ben Fry"/><br /><sub><b>Ben Fry</b></sub></a><br /><a href="https://github.com/processing/processing4-carbon-aug-19/commits?author=benfry" title="Code">💻</a> <a href="#ideas-benfry" title="Ideas, Planning, & Feedback">🤔</a> <a href="#infra-benfry" title="Infrastructure (Hosting, Build-Tools, etc)">🚇</a> <a href="#mentoring-benfry" title="Mentoring">🧑‍🏫</a> <a href="#maintenance-benfry" title="Maintenance">🚧</a> <a href="#content-benfry" title="Content">🖋</a> <a href="#talk-benfry" title="Talks">📢</a></td>
    </tr>
  </tbody>
</table>

<!-- markdownlint-restore -->
<!-- prettier-ignore-end -->

<!-- ALL-CONTRIBUTORS-LIST:END -->
