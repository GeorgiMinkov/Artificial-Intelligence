﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Xml.Serialization;

namespace PhotoStudio.Models
{
    public class PhotoTripods
    {
        [XmlElement("tripod")]
        public List<Tripod> tripodsList = new List<Tripod>();
    }
}